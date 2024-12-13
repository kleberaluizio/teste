package com.gesplan.calculadoradeemprestimo.service;

import static org.junit.jupiter.api.Assertions.*;

import com.gesplan.calculadoradeemprestimo.exception.FirstPaymentDateOutOfRangeException;
import com.gesplan.calculadoradeemprestimo.exception.InitialDateAfterFinalDateException;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LoanCalculatorServiceImplTest
{
	static LoanInfoDTO loanInfoDTO = new LoanInfoDTO();
	LoanCalculatorService loanCalculatorService = new LoanCalculatorServiceImpl();

	@DisplayName("Should throws InitialDateAfterFinalDateException")
	@ParameterizedTest(name = "{2}")
	@CsvSource({
		"2024-08-30, 2024-08-24, ... when initialDate after finalDate",
		"2024-01-30, 2024-01-30, ... when initialDate and finalDate are equals"})
	public void shouldThrowsInitialDateAfterFinalDateException(String initialDate, String finalDate, String displayName)
	{
		loanInfoDTO.setInitialDate(initialDate);
		loanInfoDTO.setFinalDate(finalDate);

		assertThrows(InitialDateAfterFinalDateException.class, () -> loanCalculatorService.getLoanFinancialSummary(loanInfoDTO));
	}

	@DisplayName("Should throws FirstPaymentDateOutOfRangeException")
	@ParameterizedTest(name = "{3}")
	@CsvSource({
		"2024-08-01, 2034-08-01, 2034-10-15, ... when first payment after final date",
		"2024-08-01, 2034-08-01, 2024-05-15, ... when initialDate and finalDate are equals"})
	public void shouldThrowsFirstPaymentDateOutOfRangeException(String initialDate, String finalDate,
		String paymentDate, String displayName)
	{
		loanInfoDTO.setInitialDate(initialDate);
		loanInfoDTO.setFinalDate(finalDate);
		loanInfoDTO.setFirstPaymentDate(paymentDate);

		assertThrows(FirstPaymentDateOutOfRangeException.class, () -> loanCalculatorService.getLoanFinancialSummary(loanInfoDTO));
	}

}