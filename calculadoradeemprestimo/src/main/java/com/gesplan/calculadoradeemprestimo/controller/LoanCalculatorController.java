package com.gesplan.calculadoradeemprestimo.controller;

import com.gesplan.calculadoradeemprestimo.exception.InitialDateAfterFinalDateException;
import com.gesplan.calculadoradeemprestimo.exception.FirstPaymentDateOutOfRangeException;
import com.gesplan.calculadoradeemprestimo.model.LoanScheduleEntry;
import com.gesplan.calculadoradeemprestimo.model.dto.LoanInfoDTO;
import com.gesplan.calculadoradeemprestimo.service.LoanCalculatorService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan")
public class LoanCalculatorController
{

	private final LoanCalculatorService service;

	public LoanCalculatorController(LoanCalculatorService loanCalculatorService)
	{
		this.service = loanCalculatorService;
	}


	@PostMapping("/financial-summary")
	public ResponseEntity<?> assembleFinancialSummary(@RequestBody @Valid LoanInfoDTO loanInfoDTO)
	{
		try
		{
			List<LoanScheduleEntry> financialSummaries = service.getLoanFinancialSummary(loanInfoDTO);

			return ResponseEntity.status(HttpStatus.OK).body(
				financialSummaries
					.stream()
					.map(LoanScheduleEntry::convertToDTO)
					.collect(Collectors.toList())
			);
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
