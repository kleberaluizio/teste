package com.gesplan.calculadoradeemprestimo.service;

import com.gesplan.calculadoradeemprestimo.model.LoanFinancialSummary;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInputInfoDTO;
import java.util.List;

public interface LoanCalculatorService
{
	List<LoanFinancialSummary> getLoanFinancialSummary(LoanInputInfoDTO dto);
}
