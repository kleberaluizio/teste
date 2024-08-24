package com.gesplan.calculadoradeemprestimo.exception;

public class InitialDateAfterFinalDateException extends RuntimeException
{
	public InitialDateAfterFinalDateException()
	{
		super("Initial date should be before final date");
	}
}
