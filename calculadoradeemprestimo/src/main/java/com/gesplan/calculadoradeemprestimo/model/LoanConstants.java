package com.gesplan.calculadoradeemprestimo.model;

import com.gesplan.calculadoradeemprestimo.model.dto.LoanInputInfoDTO;
import java.time.temporal.ChronoUnit;

public class LoanConstants
{
	private final int TOTAL_INSTALLMENTS;
	private final double INTEREST_RATE;
	private final double AMORTIZATION_VALUE;

	public LoanConstants(LoanInputInfoDTO loanInfo)
	{
		this.TOTAL_INSTALLMENTS = getInstallmentsNumber(loanInfo);
		this.INTEREST_RATE = loanInfo.getInterestRate();
		this.AMORTIZATION_VALUE = loanInfo.getLoanAmount()/TOTAL_INSTALLMENTS;
	}

	public int getTotalInstallments()
	{
		return TOTAL_INSTALLMENTS;
	}

	public double getInterestRate()
	{
		return INTEREST_RATE;
	}

	public double getAmortizationValue()
	{
		return AMORTIZATION_VALUE;
	}

	private int getInstallmentsNumber(LoanInputInfoDTO dto)
	{
		//assertFinalGreaterThanInitial

		return (int) ChronoUnit.MONTHS.between(dto.getInitialDate(), dto.getFinalDate());
	}
}
