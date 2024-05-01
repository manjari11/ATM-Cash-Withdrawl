package com.citibank.atm.cashwithdrawl.exceptions;

public class InvalidAmountException extends Exception{
	
	
	private static final long serialVersionUID = 1L;
	String errorMessage;
	
	public InvalidAmountException() {
		super();
	}
	
	public InvalidAmountException(String errorMessage) {
		super(errorMessage);
	}

}
