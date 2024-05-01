package com.citibank.atm.cashwithdrawl.pojos;

public class Account {

	private String name;
	private int accNumber;
	private double balance;
	
	public Account(String name,int accNumber,double balance) {
		this.name = name;
		this.accNumber = accNumber;
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(int accNumber) {
		this.accNumber = accNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}


	public String toString() {
		return "Owner Name :: " + name + " || Account Number :: " + accNumber + " || Balance Amount :: " + balance;
	}

}
