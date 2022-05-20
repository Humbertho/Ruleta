package com.ibm.academia.ruleta.exceptions;

public class NotFoundException extends RuntimeException
{
	public NotFoundException(String message)
	{
		super(message);
	}
	
	private static final long serialVersionUID = 1L;

}
