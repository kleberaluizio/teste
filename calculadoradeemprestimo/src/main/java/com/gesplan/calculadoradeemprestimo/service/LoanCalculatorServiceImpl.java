package com.gesplan.calculadoradeemprestimo.service;

import com.gesplan.calculadoradeemprestimo.model.LoanConstants;
import com.gesplan.calculadoradeemprestimo.model.LoanFinancialSummary;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInputInfoDTO;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LoanCalculatorServiceImpl implements LoanCalculatorService
{
	private final static int BASE_DAYS = 360;

	@Override
	public List<LoanFinancialSummary> getLoanFinancialSummary(LoanInputInfoDTO loanInfo)
	{
		LoanConstants loanConstants = new LoanConstants(loanInfo);

		LocalDate firstPaymentDate = loanInfo.getFirstPaymentDate();

		LoanFinancialSummary loanFinancialSummary = new LoanFinancialSummary();

		List<LoanFinancialSummary> summariesBeforeStartPaying = getSummariesBeforeStartPaying(loanInfo);
		double accumullated = getAccumullated(loanFinancialSummary)
		loanFinancialSummary.setAccumulated(accumullated);


		List<LoanFinancialSummary> LoanFinancialSummary = new ArrayList<>();
		return List.of();
	}



	private List<LoanFinancialSummary> getSummariesBeforeStartPaying(LoanInputInfoDTO loanInputInfo)
	{
		int monthsUntilPaymentStart = getMonthsUntilPaymentStart(loanInputInfo);

		if (monthsUntilPaymentStart == 0)
		{
			return List.of();
		}
		List<LoanFinancialSummary> LoanFinancialSummaries = new ArrayList<>();

		for(int i = 0; i < monthsUntilPaymentStart; i++)
		{
			LoanFinancialSummary summary = new LoanFinancialSummary();

			summary.setCompetenceDate(loanInputInfo.getInitialDate());
			summary.setLoanAmount(loanInputInfo.getLoanAmount());
			summary.setOutstandingAmount(loanInputInfo.getLoanAmount());
			summary.setConsolidated("");
			summary.setTotalPayment(0d);
			summary.setAmortization(0d);
			summary.setBalance(loanInputInfo.getLoanAmount());
			summary.setProvision(0d);
			summary.setLoanAmount(0d);
			summary.setAccumulated(0d);
			summary.setPaid(0d);

//			private String competenceDate;
//			private double loanAmount;
//			private double outstandingAmount;
//			private String Consolidated;
//			private double total;
//			private double amortization;
//			private double balance;
//			private double provision;
//			private double accumulated;
//			private double paid;


			LoanFinancialSummaries.add(summary);
		}

		return LoanFinancialSummaries;
	}
	private LoanFinancialSummary getFirstSummary(LoanInputInfoDTO loanInputInfo)
	{
		LoanFinancialSummary summary = new LoanFinancialSummary();

		summary.setCompetenceDate(loanInputInfo.getInitialDate());
		summary.setLoanAmount(loanInputInfo.getLoanAmount());
		summary.setOutstandingAmount(loanInputInfo.getLoanAmount());
		summary.setConsolidated("");
		summary.setTotalPayment(0d);
		summary.setAmortization(0d);
		summary.setBalance(loanInputInfo.getLoanAmount());
		summary.setProvision(0d);
		summary.setLoanAmount(0d);
		summary.setAccumulated(0d);
		summary.setPaid(0d);

		return summary;
	}

	private double getProvision(LoanFinancialSummary actualSummary,
		LoanFinancialSummary previousSummary)
	{

		return  BASE_DAYS - INTEREST_RATE + INSTALLMENTS + AMORTIZATION_VALUE;
	}

	private double getOutstandingAmount(LoanFinancialSummary summary)
	{
		return summary.getBalance() + summary.getAccumulated();
	}

	private double getBalance(LoanFinancialSummary actualSummary,
		LoanFinancialSummary previousSummary)
	{
		return previousSummary.getBalance() - actualSummary.getAmortization();
	}

	private double getAccumullated(LoanFinancialSummary actualSummary,
		LoanFinancialSummary previousSummary)
	{
		return previousSummary.getAccumulated() + actualSummary.getProvision() - actualSummary.getPaid();
	}

	private double getPaid(LoanFinancialSummary actualSummary,
		LoanFinancialSummary previousSummary)
	{
		if (actualSummary.getConsolidated().isBlank())
		{
			return 0;
		}

		return previousSummary.getAccumulated() + actualSummary.getProvision();
	}

	private int getMonthsUntilPaymentStart(LoanInputInfoDTO dto)
	{
		//assertFirstGreaterThanInitial

		return (int) ChronoUnit.MONTHS.between(dto.getInitialDate(), dto.getFirstPaymentDate());
	}



}
