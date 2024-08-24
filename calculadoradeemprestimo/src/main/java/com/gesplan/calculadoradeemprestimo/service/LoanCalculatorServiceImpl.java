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
	public List<LoanFinancialSummaryDTO> getLoanFinancialSummary(LoanInfoDTO loanInfoDTO)
	{
		assertValidDates(loanInfoDTO);
		setAttributes(loanInfoDTO);

		List<LoanFinancialSummary> financialSummaries = new ArrayList<>();
		createFinancialSummaries(financialSummaries);

		return financialSummaries.stream().map(LoanFinancialSummary::convertToDTO)
			.collect(Collectors.toList());
	}

	private void createFinancialSummaries(List<LoanFinancialSummary> financialSummaries)
	{
		createFirstSummary(financialSummaries);
		createPrePaymentsSummaries(financialSummaries);
		createRemainingSummaries(financialSummaries);
	}

	private void createFirstSummary(List<LoanFinancialSummary> summaries)
	{
		LoanFinancialSummary firstSummary = new LoanFinancialSummary(loanInfo);
		summaries.add(firstSummary);
	}

	private void createPrePaymentsSummaries(List<LoanFinancialSummary> summaries)
	{
		if (getMonthsUntilPaymentStart(loanInfo) == 0)
		{
			return;
		}

		LocalDate compensatedDate = getLastDayOfMonth(loanInfo.getInitialDate());
		LoanFinancialSummary summary;

		while (loanInfo.getFirstPaymentDate().isAfter(compensatedDate))
		{
			summary = new LoanFinancialSummary(summaries.getLast(), loanConstants, 0, compensatedDate);

			summaries.add(summary);

			compensatedDate = compensatedDate.plusMonths(1);
		}
	}

	private void createRemainingSummaries(List<LoanFinancialSummary> summaries)
	{
		if (loanConstants.getTotalInstallments() == 0)
		{
			return;
		}

		LocalDate compensatedDate = loanInfo.getFirstPaymentDate();
		LoanFinancialSummary summary;
		int installment = 1;

		while (loanInfo.getFinalDate().isAfter(compensatedDate))
		{
			summary = new LoanFinancialSummary(summaries.getLast(), loanConstants, installment,
				compensatedDate);
			summaries.add(summary);

			if (compensatedDate.isBefore(getLastDayOfMonth(compensatedDate)))
			{
				summary = new LoanFinancialSummary(summaries.getLast(), loanConstants, 0,
					getLastDayOfMonth(compensatedDate));
				summaries.add(summary);
			}

			compensatedDate = compensatedDate.plusMonths(1);
			installment++;
		}

		summary = new LoanFinancialSummary(summaries.getLast(), loanConstants, installment,
			loanInfo.getFinalDate());

		summaries.add(summary);
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
