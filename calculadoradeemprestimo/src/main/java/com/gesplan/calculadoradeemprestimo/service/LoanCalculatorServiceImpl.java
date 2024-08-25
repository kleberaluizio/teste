package com.gesplan.calculadoradeemprestimo.service;

import com.gesplan.calculadoradeemprestimo.exception.InitialDateAfterFinalDateException;
import com.gesplan.calculadoradeemprestimo.exception.FirstPaymentDateOutOfRangeException;
import com.gesplan.calculadoradeemprestimo.model.LoanConstants;
import com.gesplan.calculadoradeemprestimo.model.LoanFinancialRecord;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LoanCalculatorServiceImpl implements LoanCalculatorService
{
	private LoanConstants loanConstants;
	private LoanInfoDTO loanInfo;

	@Override
	public List<LoanFinancialRecord> getLoanFinancialSummary(LoanInfoDTO loanInfoDTO)
	{
		assertValidDates(loanInfoDTO);
		setAttributes(loanInfoDTO);

		List<LoanFinancialRecord> financialSummary = new ArrayList<>();
		assembleFinancialSummary(financialSummary);

		return financialSummary;
	}

	private void assembleFinancialSummary(List<LoanFinancialRecord> financialSummary)
	{
		createFirstRecord(financialSummary);

		if (getMonthsUntilPaymentStart(loanInfo) > 0)
		{
			createPrePaymentsLastDayOfMonthRecords(financialSummary);
		}

		createRemainingRecords(financialSummary);
	}

	private void createFirstRecord(List<LoanFinancialRecord> financialSummary)
	{
		LoanFinancialRecord firstRecord = new LoanFinancialRecord(loanInfo);
		financialSummary.add(firstRecord);
	}

	private void createPrePaymentsLastDayOfMonthRecords(List<LoanFinancialRecord> financialSummary)
	{
		LocalDate lastDayOfMonth = getLastDayOfMonth(loanInfo.getInitialDate());

		while (loanInfo.getFirstPaymentDate().isAfter(lastDayOfMonth))
		{
			addNoInstallmentRecord(financialSummary,lastDayOfMonth);
			lastDayOfMonth = getLastDayOfMonth(lastDayOfMonth.plusMonths(1));
		}
	}

	private void createRemainingRecords(List<LoanFinancialRecord> financialSummary)
	{
		LocalDate paymentDate = loanInfo.getFirstPaymentDate();
		int installment = 1;

		while (loanInfo.getFinalDate().isAfter(paymentDate))
		{
			addInstallmentRecord(financialSummary, installment, paymentDate);

			LocalDate lastDayOfMonth = getLastDayOfMonth(paymentDate);
			if (paymentDate.isBefore(lastDayOfMonth))
			{
				addNoInstallmentRecord(financialSummary, lastDayOfMonth);
			}

			paymentDate = getNextPaymentDate(paymentDate);
			installment++;
		}

		// Add the last installment record on the final date of the loan
		addInstallmentRecord(financialSummary, installment, loanInfo.getFinalDate());
	}

	private void addInstallmentRecord(List<LoanFinancialRecord> financialSummary, int installment,
		LocalDate date)
	{
		financialSummary.add(
			new LoanFinancialRecord(financialSummary.getLast(), loanConstants, installment, date)
		);
	}

	private void addNoInstallmentRecord(List<LoanFinancialRecord> financialSummary, LocalDate date)
	{
		financialSummary.add(
			new LoanFinancialRecord(financialSummary.getLast(), loanConstants, 0, date)
		);
	}

	private LocalDate getNextPaymentDate(LocalDate paymentDate)
	{
		int FEBRUARY = 2;
		int firstPaymentDay = loanInfo.getFirstPaymentDate().getDayOfMonth();

		if (firstPaymentDay == 31)
		{
			return getLastDayOfMonth(paymentDate.plusMonths(1));
		}

		if (firstPaymentDay == 30 && paymentDate.getMonthValue() == FEBRUARY)
		{
			return paymentDate.plusMonths(1).plusDays(1);
		}

		return paymentDate.plusMonths(1);
	}

	private int getMonthsUntilPaymentStart(LoanInfoDTO dto)
	{
		return (int) ChronoUnit.MONTHS.between(dto.getInitialDate(), dto.getFirstPaymentDate());
	}

	private LocalDate getLastDayOfMonth(LocalDate date)
	{
		return date.withDayOfMonth(date.lengthOfMonth());
	}

	private void setAttributes(LoanInfoDTO loanInfoDTO)
	{
		loanConstants = new LoanConstants(loanInfoDTO);
		loanInfo = loanInfoDTO;
	}

	private void assertValidDates(LoanInfoDTO dto)
	{
		assertFinalDateAfterInitialDate(dto);
		assertFirstPaymentDateInRange(dto);
	}

	private void assertFinalDateAfterInitialDate(LoanInfoDTO dto)
	{
		if (dto.getInitialDate().isAfter(dto.getFinalDate()))
		{
			throw new InitialDateAfterFinalDateException();
		}
	}

	private void assertFirstPaymentDateInRange(LoanInfoDTO dto)
	{
		if (dto.getFirstPaymentDate().isBefore(dto.getInitialDate()) || dto.getFirstPaymentDate()
			.isAfter(dto.getFinalDate()))
		{
			throw new FirstPaymentDateOutOfRangeException();
		}
	}
}
