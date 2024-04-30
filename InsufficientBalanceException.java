package com.assignment.atmcashwithdrawl.exceptions;

public class InsufficientBalanceException extends Exception{
	
	
	private static final long serialVersionUID = 1L;
	String errorMessage;
	
	public InsufficientBalanceException() {
		super();
	}
	
	public InsufficientBalanceException (String errorMessage) {
		super(errorMessage);
	}
	

}
