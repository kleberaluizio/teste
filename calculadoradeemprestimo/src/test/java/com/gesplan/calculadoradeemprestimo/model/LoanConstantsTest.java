package com.gesplan.calculadoradeemprestimo.model;

import static org.junit.jupiter.api.Assertions.*;

import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LoanConstantsTest
{
	@DisplayName("Should Return Correct number of Installments When FinalDateDay Less Than FirstPaymentDateDay")
	@Test
	public void shouldReturnCorrectTotalInstallmentsWhenFinalDateDayLessThanFirstPaymentDateDay()
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

	@DisplayName("Should Return Correct number of Installments When FinalDateDay Greater Than FirstPaymentDateDay")
	@Test
	public void shouldReturnCorrectTotalInstallmentsWhenFinalDateDayGreaterThanFirstPaymentDateDay()
	{
		// Arrange
		LoanInfoDTO loanInfoDTO = createGenericInfoDTO();
		loanInfoDTO.setInitialDate("2024-01-01");
		loanInfoDTO.setFinalDate("2034-01-16");
		loanInfoDTO.setFirstPaymentDate("2024-02-15");

		LoanConstants constants = new LoanConstants(loanInfoDTO);
		int EXPECTED_INSTALLMENTS = 121;

		//Act
		int ACTUAL_INSTALLMENTS = constants.getTotalInstallments();

		//Assert
		assertEquals(EXPECTED_INSTALLMENTS, ACTUAL_INSTALLMENTS,
			"The total number of installments should be 121");
	}

	@DisplayName("Should Return Correct number of Installments When FinalDateDay and FirstPaymentDateDay are in the same month")
	@Test
	public void shouldReturnCorrectTotalInstallmentsWhenFinalDateAndFirstPaymentAreInSameMonth()
	{
		// Arrange
		LoanInfoDTO loanInfoDTO = createGenericInfoDTO();
		loanInfoDTO.setInitialDate("2024-01-01");
		loanInfoDTO.setFinalDate("2034-01-16");
		loanInfoDTO.setFirstPaymentDate("2034-02-15");

		LoanConstants constants = new LoanConstants(loanInfoDTO);
		int EXPECTED_INSTALLMENTS = 2;

		//Act
		int ACTUAL_INSTALLMENTS = constants.getTotalInstallments();

		//Assert
		assertEquals(EXPECTED_INSTALLMENTS, ACTUAL_INSTALLMENTS,
			"The total number of installments should be 121");
	}

	@DisplayName("Should Return Correct number of Installments When FinalDateDay and FirstPaymentDateDay are equal")
	@Test
	public void shouldReturnCorrectTotalInstallmentsWhenFinalDateAndFirstPaymentAreEqual()
	{
		// Arrange
		LoanInfoDTO loanInfoDTO = createGenericInfoDTO();
		loanInfoDTO.setInitialDate("2024-01-01");
		loanInfoDTO.setFinalDate("2034-01-16");
		loanInfoDTO.setFirstPaymentDate("2034-02-16");

		LoanConstants constants = new LoanConstants(loanInfoDTO);
		int EXPECTED_INSTALLMENTS = 1;

		//Act
		int ACTUAL_INSTALLMENTS = constants.getTotalInstallments();

		//Assert
		assertEquals(EXPECTED_INSTALLMENTS, ACTUAL_INSTALLMENTS,
			"The total number of installments should be 121");
	}


	private LoanInfoDTO createGenericInfoDTO()
	{
		LoanInfoDTO dto = new LoanInfoDTO();
		dto.setInterestRate(7);
		dto.setLoanAmount(1000000.0);
		return dto;
	}

}