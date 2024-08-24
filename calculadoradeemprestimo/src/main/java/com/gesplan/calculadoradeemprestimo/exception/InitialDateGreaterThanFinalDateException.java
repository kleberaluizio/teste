package com.gesplan.calculadoradeemprestimo.exception;

public class InitialDateGreaterThanFinalDateException extends RuntimeException
{
	public InitialDateGreaterThanFinalDateException()
	{
		super("Initial date should be before final date");
	}
}
