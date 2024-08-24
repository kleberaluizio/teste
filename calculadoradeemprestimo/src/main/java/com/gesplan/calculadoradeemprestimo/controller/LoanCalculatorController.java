package com.gesplan.calculadoradeemprestimo.controller;

import com.gesplan.calculadoradeemprestimo.exception.InitialDateAfterFinalDateException;
import com.gesplan.calculadoradeemprestimo.exception.FirstPaymentDateOutOfRangeException;
import com.gesplan.calculadoradeemprestimo.model.LoanFinancialSummary;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanFinancialSummaryDTO;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import com.gesplan.calculadoradeemprestimo.service.LoanCalculatorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class LoanCalculatorController
{

	private final LoanCalculatorService service;

	public LoanCalculatorController(LoanCalculatorService loanCalculatorService)
	{
		this.service = loanCalculatorService;
	}


	@GetMapping("/financial-summaries")
	public ResponseEntity<?> getCalculateResult(@RequestBody @Valid LoanInfoDTO loanInfoDTO)
	{
		try
		{
			List<LoanFinancialSummaryDTO> financialSummaries = service.getLoanFinancialSummary(loanInfoDTO);
			return ResponseEntity.status(HttpStatus.OK).body(financialSummaries);
		}
		catch (InitialDateAfterFinalDateException | FirstPaymentDateOutOfRangeException e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e)
		{
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
