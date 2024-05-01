package com.citibank.atm.cashwithdrawl.exceptions;

public class IncorrectAmountException extends Exception{
	
	
	private static final long serialVersionUID = 1L;
	String errorMessage;
	
	public IncorrectAmountException() {
		super();
	}
	
	public IncorrectAmountException(String errorMessage) {
		super(errorMessage);
	}

}
