package com.gesplan.calculadoradeemprestimo.model;

import com.gesplan.calculadoradeemprestimo.model.dto.LoanScheduleEntryDTO;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanScheduleEntry
{
	private String competenceDate;
	private double loanAmount;
	private double outstandingAmount;
	private String consolidated;
	private double totalPayment;
	private double amortization;
	private double balance;
	private double provision;
	private double accumulated;
	private double paid;

	public LoanScheduleEntry(LoanInfoDTO loanInputInfo)
	{
		/* The order of the following lines should not be changed */
		this.setCompetenceDate(loanInputInfo.getInitialDate());
		this.consolidated = "";
		this.provision = 0.0;
		this.paid = 0.0;
		this.accumulated = 0.0;
		this.amortization = 0.0;
		this.setTotalPayment();
		this.loanAmount = loanInputInfo.getLoanAmount();
		this.balance = loanInputInfo.getLoanAmount();
		this.setOutstandingAmount();
	}

	public LoanScheduleEntry(LoanScheduleEntry previousSummary,
		LoanConstants loanConstants, int installmentNumber, LocalDate competenceDate)
	{
		/* The order of the following lines should not be changed */
		this.setCompetenceDate(competenceDate);
		this.setConsolidated(installmentNumber, loanConstants.getTotalInstallments());
		this.setProvision(previousSummary, loanConstants);
		this.setPaid(previousSummary);
		this.setAccumulated(previousSummary);
		this.setAmortization(loanConstants);
		this.setTotalPayment();
		this.setBalance(previousSummary);
		this.setOutstandingAmount();
		this.loanAmount = 0.0;
	}


	private void setCompetenceDate(LocalDate competenceDate)
	{
		this.competenceDate = competenceDate.toString();
	}

	private void setConsolidated(int installment, int TotalInstallment)
	{
		this.consolidated = installment == 0 ? "" : (installment + "/" + TotalInstallment);
	}

	private void setProvision(LoanScheduleEntry previous, LoanConstants constants)
	{
		double adjustedInterestRate = constants.getInterestRate() + 1;
		double timeFactor = getDaysBetweenSummaries(previous) / LoanConstants.BASE_DAYS;
		double totalPreviousBalance = previous.getBalance() + previous.getAccumulated();

		this.provision = ((Math.pow(adjustedInterestRate, timeFactor)) - 1) * totalPreviousBalance;
	}

	private double getDaysBetweenSummaries(LoanScheduleEntry previous)
	{
		LocalDate previousDate = LocalDate.parse(previous.getCompetenceDate());
		LocalDate actualDate = LocalDate.parse(this.competenceDate);
		return ChronoUnit.DAYS.between(previousDate, actualDate);
	}

	private void setPaid(LoanScheduleEntry previous)
	{
		this.paid = this.consolidated.isBlank() ? 0 : (previous.getAccumulated() + this.provision);
	}

	private void setAccumulated(LoanScheduleEntry previous)
	{
		this.accumulated = previous.getAccumulated() + this.provision - this.paid;
	}

	private void setAmortization(LoanConstants constants)
	{
		this.amortization = this.consolidated.isBlank() ? 0 : constants.getAmortizationValue();
	}

	private void setTotalPayment()
	{
		this.totalPayment = this.consolidated.isBlank() ? 0 : (this.amortization + this.paid);
	}

	private void setBalance(LoanScheduleEntry previous)
	{
		this.balance = previous.getBalance() - this.amortization;
	}

	private void setOutstandingAmount()
	{
		this.outstandingAmount = this.balance + this.accumulated;
	}

	private String getCompetenceDate()
	{
		return competenceDate;
	}

	private double getBalance()
	{
		return balance;
	}

	private double getAccumulated()
	{
		return accumulated;
	}

	public LoanScheduleEntryDTO convertToDTO()
	{
		return new LoanScheduleEntryDTO(
			this.competenceDate,
			this.loanAmount,
			this.outstandingAmount,
			this.consolidated,
			this.totalPayment,
			this.amortization,
			this.balance,
			this.provision,
			this.accumulated,
			this.paid
		);
	}
}
