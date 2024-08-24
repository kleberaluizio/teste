package com.gesplan.calculadoradeemprestimo.exception;

public class FirstPaymentDateOutOfRangeException extends RuntimeException
{
	public FirstPaymentDateOutOfRangeException()
	{
		super("First Payment date should be after initial date and before final date");
	}
}