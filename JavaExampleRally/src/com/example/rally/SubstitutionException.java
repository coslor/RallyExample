package com.example.rally;

public class SubstitutionException extends Exception {

	/**
	 * 	A custom Exception for substutution errors
	 */
	private static final long serialVersionUID = 1883334322028849896L;

	public SubstitutionException()
	{
		super();
	}
	
	public SubstitutionException(String message)
	{
		super(message);
	}
	
	public SubstitutionException(String message, Throwable t)
	{
		super(message, t);
	}
}
