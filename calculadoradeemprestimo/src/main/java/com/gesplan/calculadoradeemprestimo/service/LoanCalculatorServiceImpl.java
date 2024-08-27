package com.gesplan.calculadoradeemprestimo.service;

import com.gesplan.calculadoradeemprestimo.exception.InitialDateAfterFinalDateException;
import com.gesplan.calculadoradeemprestimo.exception.FirstPaymentDateOutOfRangeException;
import com.gesplan.calculadoradeemprestimo.model.LoanConstants;
import com.gesplan.calculadoradeemprestimo.model.LoanScheduleEntry;
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
	public List<LoanScheduleEntry> getLoanFinancialSummary(LoanInfoDTO loanInfoDTO)
	{
		assertValidData(loanInfoDTO);
		setAttributes(loanInfoDTO);

		List<LoanScheduleEntry> financialSummary = new ArrayList<>();
		assembleFinancialSummary(financialSummary);

		return financialSummary;
	}

	private void assembleFinancialSummary(List<LoanScheduleEntry> financialSummary)
	{
		createFirstEntry(financialSummary);

		if (getMonthsUntilPaymentStart(loanInfo) > 0)
		{
			createPrePaymentsLastDayOfMonthEntries(financialSummary);
		}

		createRemainingEntries(financialSummary);
	}

	private void createFirstEntry(List<LoanScheduleEntry> financialSummary)
	{
		LoanScheduleEntry firstRecord = new LoanScheduleEntry(loanInfo);
		financialSummary.add(firstRecord);
	}

	private void createPrePaymentsLastDayOfMonthEntries(List<LoanScheduleEntry> financialSummary)
	{
		LocalDate lastDayOfMonth = getLastDayOfMonth(loanInfo.getInitialDate());

		while (loanInfo.getFirstPaymentDate().isAfter(lastDayOfMonth))
		{
			addNoInstallmentEntry(financialSummary,lastDayOfMonth);
			lastDayOfMonth = getLastDayOfMonth(lastDayOfMonth.plusMonths(1));
		}
	}

	private void createRemainingEntries(List<LoanScheduleEntry> financialSummary)
	{
		LocalDate paymentDate = loanInfo.getFirstPaymentDate();
		int installment = 1;

		while (loanInfo.getFinalDate().isAfter(paymentDate))
		{
			addInstallmentEntry(financialSummary, installment, paymentDate);

			LocalDate lastDayOfMonth = getLastDayOfMonth(paymentDate);
			if (lastDayOfMonth.isBefore(loanInfo.getFinalDate()) && !lastDayOfMonth.isEqual(paymentDate))
			{
				addNoInstallmentEntry(financialSummary, lastDayOfMonth);
			}

			paymentDate = getNextPaymentDate(paymentDate);
			installment++;
		}

		// Add the last installment entry on the final date
		addInstallmentEntry(financialSummary, installment, loanInfo.getFinalDate());
	}

	private void addInstallmentEntry(List<LoanScheduleEntry> financialSummary, int installment,
		LocalDate date)
	{
		financialSummary.add(
			new LoanScheduleEntry(financialSummary.getLast(), loanConstants, installment, date)
		);
	}

	private void addNoInstallmentEntry(List<LoanScheduleEntry> financialSummary, LocalDate date)
	{
		financialSummary.add(
			new LoanScheduleEntry(financialSummary.getLast(), loanConstants, 0, date)
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

	private void assertValidData(LoanInfoDTO dto)
	{
		assertFinalDateAfterInitialDate(dto);
		assertFirstPaymentDateInRange(dto);
		assertLoanAmountAndInterestRateArePositives(dto);
	}

	private void assertFinalDateAfterInitialDate(LoanInfoDTO dto)
	{
		if (dto.getInitialDate().isAfter(dto.getFinalDate()) ||
			dto.getInitialDate().isEqual(dto.getFinalDate()))
		{
			throw new InitialDateAfterFinalDateException();
		}
	}

	private void assertFirstPaymentDateInRange(LoanInfoDTO dto)
	{
		if (!dto.getFirstPaymentDate().isAfter(dto.getInitialDate()) ||
			!dto.getFirstPaymentDate().isBefore(dto.getFinalDate()) )
		{
			throw new FirstPaymentDateOutOfRangeException();
		}
	}

	private void assertLoanAmountAndInterestRateArePositives(LoanInfoDTO dto)
	{
		if (dto.getLoanAmount() <= 0) {
			throw new IllegalArgumentException("Loan amount should be positive!");
		}
		if (dto.getInterestRate() <= 0) {
			throw new IllegalArgumentException("Interest rate should be positive!");
		}
	}
}
