package com.gesplan.calculadoradeemprestimo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.gesplan.calculadoradeemprestimo.exception.FirstPaymentDateOutOfRangeException;
import com.gesplan.calculadoradeemprestimo.exception.InitialDateAfterFinalDateException;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LoanCalculatorServiceImplTest
{
	LoanCalculatorService loanCalculatorService = new LoanCalculatorServiceImpl();

	@DisplayName("Should throws InitialDateAfterFinalDateException when initialDate after finalDate")
	@Test
	public void shouldThrowsInitialDateAfterFinalDateExceptionWhenInitialDateAfterFinalDate()
	{
		LoanInfoDTO loanInfoDTO = createGenericInfoDTO();
		loanInfoDTO.setInitialDate("2024-08-30");
		loanInfoDTO.setFinalDate("2024-08-24");

		assertThrows(InitialDateAfterFinalDateException.class, () -> {
			loanCalculatorService.getLoanFinancialSummary(loanInfoDTO);
		});
	}

	@DisplayName("Should throws InitialDateAfterFinalDateException when initialDate equals finalDate")
	@Test
	public void shouldThrowsInitialDateAfterFinalDateExceptionWhenInitialDateEqualsFinalDate()
	{
		LoanInfoDTO loanInfoDTO = createGenericInfoDTO();
		loanInfoDTO.setInitialDate("2024-01-30");
		loanInfoDTO.setFinalDate("2024-01-30");

		assertThrows(InitialDateAfterFinalDateException.class, () -> {
			loanCalculatorService.getLoanFinancialSummary(loanInfoDTO);
		});
	}

	@DisplayName("Should throws FirstPaymentDateOutOfRangeException When First Payment After Final Date")
	@Test
	public void shouldThrowsFirstPaymentDateOutOfRangeExceptionWhenFirstPaymentAfterFinalDate()
	{
		LoanInfoDTO loanInfoDTO = createGenericInfoDTO();
		loanInfoDTO.setInitialDate("2024-08-01");
		loanInfoDTO.setFinalDate("2034-08-01");
		loanInfoDTO.setFirstPaymentDate("2034-10-15");

		assertThrows(FirstPaymentDateOutOfRangeException.class, () -> {
			loanCalculatorService.getLoanFinancialSummary(loanInfoDTO);
		});
	}

	@DisplayName("Should throws FirstPaymentDateOutOfRangeException When First Payment Before Initial Date")
	@Test
	public void shouldThrowFirstPaymentDateOutOfRangeExceptionWhenFirstPaymentBeforeInitialDate()
	{
		LoanInfoDTO loanInfoDTO = createGenericInfoDTO();
		loanInfoDTO.setInitialDate("2024-08-01");
		loanInfoDTO.setFinalDate("2034-08-01");
		loanInfoDTO.setFirstPaymentDate("2024-05-15");

		assertThrows(FirstPaymentDateOutOfRangeException.class, () -> {
			loanCalculatorService.getLoanFinancialSummary(loanInfoDTO);
		});
	}

	private LoanInfoDTO createGenericInfoDTO()
	{
		LoanInfoDTO dto = new LoanInfoDTO();
		dto.setInterestRate(7);
		dto.setLoanAmount(1000000.0);
		return dto;
	}
}