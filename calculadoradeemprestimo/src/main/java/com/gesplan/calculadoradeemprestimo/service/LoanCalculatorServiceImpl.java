package com.gesplan.calculadoradeemprestimo.service;

import com.gesplan.calculadoradeemprestimo.exception.InitialDateAfterFinalDateException;
import com.gesplan.calculadoradeemprestimo.exception.FirstPaymentDateOutOfRangeException;
import com.gesplan.calculadoradeemprestimo.model.LoanConstants;
import com.gesplan.calculadoradeemprestimo.model.LoanFinancialSummary;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanFinancialSummaryDTO;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LoanCalculatorServiceImpl implements LoanCalculatorService
{
	private LoanConstants loanConstants;
	private LoanInfoDTO loanInfo;

	@Override
	public List<LoanFinancialSummary> getLoanFinancialSummary(LoanInfoDTO loanInfoDTO)
	{
		assertValidDates(loanInfoDTO);
		setAttributes(loanInfoDTO);

		List<LoanFinancialSummary> financialSummaries = new ArrayList<>();
		createFinancialSummaries(financialSummaries);

		return financialSummaries;
	}

	private void createFinancialSummaries(List<LoanFinancialSummary> financialSummaries)
	{
		createFirstSummary(financialSummaries);

		if (getMonthsUntilPaymentStart(loanInfo) > 0)
		{
			createPrePaymentsLastDayOfMonthSummaries(financialSummaries);
		}

		if (loanConstants.getTotalInstallments() > 0)
		{
			createRemainingSummaries(financialSummaries);
		}
	}

	private void createFirstSummary(List<LoanFinancialSummary> summaries)
	{
		LoanFinancialSummary firstSummary = new LoanFinancialSummary(loanInfo);
		summaries.add(firstSummary);
	}

	private void createPrePaymentsLastDayOfMonthSummaries(List<LoanFinancialSummary> summaries)
	{
		LocalDate lastDayOfMonth = getLastDayOfMonth(loanInfo.getInitialDate());

		while (loanInfo.getFirstPaymentDate().isAfter(lastDayOfMonth))
		{
			addNoInstallmentSummary(summaries,lastDayOfMonth);
			lastDayOfMonth = lastDayOfMonth.plusMonths(1);
		}
	}

	private void createRemainingSummaries(List<LoanFinancialSummary> summaries)
	{
		LocalDate paymentDate = loanInfo.getFirstPaymentDate();
		int installment = 1;

		while (loanInfo.getFinalDate().isAfter(paymentDate))
		{
			addInstallmentSummary(summaries, installment, paymentDate);

			LocalDate lastDayOfMonth = getLastDayOfMonth(paymentDate);
			if (paymentDate.isBefore(lastDayOfMonth))
			{
				addNoInstallmentSummary(summaries, lastDayOfMonth);
			}

			paymentDate = paymentDate.plusMonths(1);
			installment++;
		}

		// Add the last installment on the final date of the loan
		addInstallmentSummary(summaries, installment, loanInfo.getFinalDate());
	}

	private void addInstallmentSummary(List<LoanFinancialSummary> summaries, int installment,
		LocalDate date)
	{
		summaries.add(
			new LoanFinancialSummary(summaries.getLast(), loanConstants, installment, date)
		);
	}

	private void addNoInstallmentSummary(List<LoanFinancialSummary> summaries, LocalDate date)
	{
		summaries.add(
			new LoanFinancialSummary(summaries.getLast(), loanConstants, 0, date)
		);
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
