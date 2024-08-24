package com.gesplan.calculadoradeemprestimo.controller;

import com.gesplan.calculadoradeemprestimo.model.LoanFinancialSummary;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInputInfoDTO;
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

	private final LoanCalculatorService loanCalculatorService;

	public LoanCalculatorController(LoanCalculatorService loanCalculatorService)
	{
		this.loanCalculatorService = loanCalculatorService;
	}


	@GetMapping
	public ResponseEntity<List<LoanFinancialSummary>> getCalculateResult(
		@RequestBody @Valid LoanInputInfoDTO loanInputInfoDTO)
	{
		List<LoanFinancialSummary> loanFinancialSummary = loanCalculatorService.getLoanFinancialSummary(
			loanInputInfoDTO);

		return ResponseEntity.status(HttpStatus.OK).body(loanFinancialSummary);
	}
}
