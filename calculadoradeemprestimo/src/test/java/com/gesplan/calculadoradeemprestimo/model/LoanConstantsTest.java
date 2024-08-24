package com.gesplan.calculadoradeemprestimo.model;

import static org.junit.jupiter.api.Assertions.*;

import com.gesplan.calculadoradeemprestimo.exception.FirstPaymentDateOutOfRangeException;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LoanConstantsTest
{
	@DisplayName("Should Return Correct TotalInstallments")
	@Test
	public void shouldReturnCorrectTotalInstallments()
	{
		// Arrange
		LoanInfoDTO loanInfoDTO = createGenericInfoDTO();
		loanInfoDTO.setInitialDate("2024-01-01");
		loanInfoDTO.setFinalDate("2034-01-01");
		loanInfoDTO.setFirstPaymentDate("2024-02-15");

		LoanConstants constants = new LoanConstants(loanInfoDTO);
		int EXPECTED_INSTALLMENTS = 120;

		//Act
		int ACTUAL_INSTALLMENTS = constants.getTotalInstallments();

		//Assert
		assertEquals(EXPECTED_INSTALLMENTS, ACTUAL_INSTALLMENTS,
			"The total number of installments should be 120");
	}

	private LoanInfoDTO createGenericInfoDTO()
	{
		LoanInfoDTO dto = new LoanInfoDTO();
		dto.setInterestRate(7);
		dto.setLoanAmount(1000000.0);
		return dto;
	}

}