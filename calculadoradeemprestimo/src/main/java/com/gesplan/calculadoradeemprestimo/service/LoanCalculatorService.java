package com.gesplan.calculadoradeemprestimo.service;

import com.gesplan.calculadoradeemprestimo.model.LoanFinancialRecord;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface LoanCalculatorService
{
	List<LoanFinancialRecord> getLoanFinancialSummary(LoanInfoDTO dto);
}
