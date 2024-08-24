package com.gesplan.calculadoradeemprestimo.service;

import com.gesplan.calculadoradeemprestimo.model.LoanFinancialSummary;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface LoanCalculatorService
{
	List<LoanFinancialSummary> getLoanFinancialSummary(LoanInfoDTO dto);
}
