package com.gesplan.calculadoradeemprestimo.model.dto;

public record LoanFinancialRecordDTO(
	String competenceDate,
	double loanAmount,
	double outstandingAmount,
	String consolidated,
	double totalPayment,
	double amortization,
	double balance,
	double provision,
	double accumulated,
	double paid
)
{ }
