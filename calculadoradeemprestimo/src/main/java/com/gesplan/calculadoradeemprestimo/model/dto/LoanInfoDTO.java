package com.gesplan.calculadoradeemprestimo.model.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoanInfoDTO
{

	@NotNull
	private LocalDate initialDate;
	@NotNull
	private LocalDate finalDate;
	@NotNull
	private LocalDate firstPaymentDate;
	@NotNull
	private double loanAmount;
	@NotNull
	private double interestRate;

	public @NotNull LocalDate getInitialDate()
	{
		return initialDate;
	}

	public void setInitialDate(@NotNull String initialDate)
	{
		this.initialDate = parseToLocalDate(initialDate);
	}

	public @NotNull LocalDate getFinalDate()
	{
		return finalDate;
	}

	public void setFinalDate(@NotNull String finalDate)
	{
		this.finalDate = parseToLocalDate(finalDate);
	}

	public @NotNull LocalDate getFirstPaymentDate()
	{
		return firstPaymentDate;
	}

	public void setFirstPaymentDate(@NotNull String firstPaymentDate)
	{
		this.firstPaymentDate = parseToLocalDate(firstPaymentDate);
	}

	public @NotNull double getLoanAmount()
	{
		return loanAmount;
	}

	public void setLoanAmount(@NotNull double loanAmount)
	{
		this.loanAmount = loanAmount;
	}

	public @NotNull double getInterestRate()
	{
		return interestRate;
	}

	public void setInterestRate(@NotNull double interestRate)
	{
		this.interestRate = interestRate;
	}

	private LocalDate parseToLocalDate(String date)
	{
		return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
	}
}
