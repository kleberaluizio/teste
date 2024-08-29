package com.gesplan.calculadoradeemprestimo.util;

import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;

public class LoanInfoDTOBuilder
{

	private String initialDate;
	private String finalDate;
	private String firstPaymentDate;
	private double loanAmount;
	private double interestRate;
	private final LoanInfoDTO loanInfoDTO = new LoanInfoDTO();

	public LoanInfoDTOBuilder() {}

	public LoanInfoDTOBuilder oneLoanInfoDTO()
	{
		this.initialDate = "2024-01-01";
		this.finalDate = "2034-01-01";
		this.firstPaymentDate = "2024-02-15";
		this.loanAmount = 140000;
		this.interestRate = 7.0;
		return this;
	}

	public LoanInfoDTOBuilder withInitialDate(String initialDate)
	{
		this.initialDate = initialDate;
		return this;
	}

	public LoanInfoDTOBuilder withFinalDate(String finalDate)
	{
		this.finalDate = finalDate;
		return this;
	}

	public LoanInfoDTOBuilder withFirstPaymentDate(String firstPaymentDate)
	{
		this.firstPaymentDate = firstPaymentDate;
		return this;
	}

	public LoanInfoDTOBuilder withLoanAmount(double loanAmount)
	{
		this.loanAmount = loanAmount;
		return this;
	}

	public LoanInfoDTOBuilder withInterestRate(double loanAmount)
	{
		this.loanAmount = loanAmount;
		return this;
	}

	public LoanInfoDTO build()
	{
		loanInfoDTO.setInitialDate(initialDate);
		loanInfoDTO.setFinalDate(finalDate);
		loanInfoDTO.setFirstPaymentDate(firstPaymentDate);
		loanInfoDTO.setLoanAmount(loanAmount);
		loanInfoDTO.setInterestRate(interestRate);

		return loanInfoDTO;
	}
}
