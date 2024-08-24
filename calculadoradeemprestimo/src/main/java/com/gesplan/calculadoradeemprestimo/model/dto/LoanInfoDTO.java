package com.gesplan.calculadoradeemprestimo.model.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoanInfoDTO
{

	private LocalDate initialDate;
	private LocalDate finalDate;
	private LocalDate firstPaymentDate;
	private double loanAmount;
	private double interestRate;

	public LocalDate getInitialDate()
	{
		return initialDate;
	}

	public void setInitialDate(String initialDate)
	{
		this.initialDate = parseToLocalDate(initialDate);
	}

	public  LocalDate getFinalDate()
	{
		return finalDate;
	}

	public void setFinalDate(String finalDate)
	{
		this.finalDate = parseToLocalDate(finalDate);
	}

	public  LocalDate getFirstPaymentDate()
	{
		return firstPaymentDate;
	}

	public void setFirstPaymentDate(String firstPaymentDate)
	{
		this.firstPaymentDate = parseToLocalDate(firstPaymentDate);
	}

	public  double getLoanAmount()
	{
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public  double getInterestRate()
	{
		return interestRate;
	}

	public void setInterestRate(double interestRate)
	{
		this.interestRate = interestRate;
	}

	private LocalDate parseToLocalDate(String date)
	{
		return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
	}
}
