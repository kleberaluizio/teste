package com.gesplan.calculadoradeemprestimo.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanFinancialSummary
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

	public LoanFinancialSummary(){}

	public LoanFinancialSummary(LoanFinancialSummary previousSummary, LoanConstants loanConstants, int installment)
	{
		setConsolidated(installment, loanConstants.getTotalInstallments());
	}
	public String getCompetenceDate()
	{
		return competenceDate;
	}

	public void setCompetenceDate(LocalDate competenceDate)
	{
		this.competenceDate = competenceDate.toString();
	}

	public double getLoanAmount()
	{
		return this.loanAmount;
	}

	public void setLoanAmount(double loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public double getOutstandingAmount()
	{
		return outstandingAmount;
	}

	public void setOutstandingAmount(double outstandingAmount)
	{
		this.outstandingAmount = outstandingAmount;
	}

	public String getConsolidated()
	{
		return consolidated;
	}

	public void setConsolidated(String consolidated)
	{
		this.consolidated = consolidated;
	}

	public double getTotalPayment()
	{
		return totalPayment;
	}

	public void setTotalPayment(double totalPayment)
	{
		this.totalPayment = totalPayment;
	}

	public double getAmortization()
	{
		return amortization;
	}

	public void setAmortization(double amortization)
	{
		this.amortization = amortization;
	}

	public double getBalance()
	{
		return balance;
	}

	public void setBalance(double balance)
	{
		this.balance = balance;
	}

	public double getProvision()
	{
		return provision;
	}

	public void setProvision(double provision)
	{
		this.provision = provision;
	}

	public double getAccumulated()
	{
		return accumulated;
	}

	public void setAccumulated(double accumulated)
	{
		this.accumulated = accumulated;
	}

	public double getPaid()
	{
		return paid;
	}

	public void setPaid(double paid)
	{
		this.paid = paid;
	}

	private void setConsolidated(int installment, int TotalInstallment )
	{
		this.consolidated = String.format("%d/%d", installment, TotalInstallment);
	}

	private void setProvision(LoanFinancialSummary previous, LoanConstants constants)
	{
		double adjustedInterestRate = constants.getInterestRate() + 1;
		double timeFactor = getDaysBetweenSummaries(previous) / constants.getTotalInstallments();
		double totalPreviousBalance = previous.getBalance() + previous.getAccumulated();

		this.provision = ((Math.pow(adjustedInterestRate, timeFactor)) - 1) * totalPreviousBalance;
	}

	private void setPaid(LoanFinancialSummary previousSummary)
	{
		if (this.getConsolidated().isBlank())
		{
			this.paid = 0;
		}

		this.paid =  previousSummary.getAccumulated() + this.getProvision();
	}

	private void setAccumulated(LoanFinancialSummary previousSummary)
	{
		this.accumulated = previousSummary.getAccumulated() + this.getProvision() - this.getPaid();
	}
	
	private double getDaysBetweenSummaries(LoanFinancialSummary previousSummary)
	{
		LocalDate previous = LocalDate.parse(previousSummary.getCompetenceDate());
		LocalDate actual = LocalDate.parse(this.competenceDate);
		return ChronoUnit.MONTHS.between(previous, actual);
	}

	private void setOutstandingAmount()
	{
		this.outstandingAmount = this.getBalance() + this.getAccumulated();
	}

	public void setTotalPayment()
	{
		this.totalPayment = this.amortization + this.paid;
	}

	private void setBalance(LoanFinancialSummary previousSummary)
	{
		this.balance = previousSummary.getBalance() - this.getAmortization();
	}



}
