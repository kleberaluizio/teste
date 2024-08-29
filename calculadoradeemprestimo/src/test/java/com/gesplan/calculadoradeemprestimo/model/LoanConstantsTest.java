package com.gesplan.calculadoradeemprestimo.model;

import static org.junit.jupiter.api.Assertions.*;

import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import com.gesplan.calculadoradeemprestimo.util.LoanInfoDTOBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class LoanConstantsTest
{
	@DisplayName("Should Return Correct number of Installments")
	@ParameterizedTest(name = "{4}")
	@CsvSource(value = {
		"2024-01-01, 2034-01-01, 2024-02-15, 120, ... when FinalDate day of month before FirstPaymentDate day of month",
		"2024-01-01, 2034-01-16, 2024-02-15, 121, ... when FinalDate day of month after FirstPaymentDate day of month",
		"2024-01-01, 2034-01-16, 2034-02-15, 2, ... when FinalDate and FirstPaymentDate are in the same month"})
	public void shouldReturnCorrectNumbersOfInstallments(String initialDate, String finalDate,
		String firstPaymentDate, int EXPECTED_INSTALLMENTS, String displayName)
	{
		// Arrange
		LoanInfoDTOBuilder builder = new LoanInfoDTOBuilder();
		LoanInfoDTO loanInfo = builder.oneLoanInfoDTO().withInitialDate(initialDate)
			.withFinalDate(finalDate).withFirstPaymentDate(firstPaymentDate).build();

		LoanConstants constants = new LoanConstants(loanInfo);

		// Act
		int ACTUAL_INSTALLMENTS = constants.getTotalInstallments();

		// Assert
		assertEquals(EXPECTED_INSTALLMENTS, ACTUAL_INSTALLMENTS);
	}
}