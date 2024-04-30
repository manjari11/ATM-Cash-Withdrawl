package com.assignment.atmcashwithdrawl.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.assignment.atmcashwithdrawl.exceptions.IncorrectAmountException;
import com.assignment.atmcashwithdrawl.exceptions.InsufficientBalanceException;
import com.assignment.atmcashwithdrawl.pojos.Account;



public class AccountOperations implements Runnable {

	private Account account;
	private double withdrawlAmount;
	private String errorMessage;
	private Map<Integer, Integer> dispensedNotes;
	private static final List<Integer> possibleNotes = Arrays.asList(500, 200, 100, 50, 20, 10);

	public AccountOperations(Account account, double amount) {
		this.account = account;
		this.withdrawlAmount = amount;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void cashwithdrawl() {
		try {
			validateWithdrawl();
			
			synchronized (this) {
				double remainingAmount = account.getBalance() - withdrawlAmount;
				account.setBalance(remainingAmount);
				dispenseAmount();
			}
		} catch (IncorrectAmountException | InsufficientBalanceException e) {
			errorMessage = e.getMessage();
		}
		
		printMessage();

	}

	private void validateWithdrawl() throws IncorrectAmountException, InsufficientBalanceException {
		if (withdrawlAmount <= 0 || withdrawlAmount % 10 != 0) {
			throw new IncorrectAmountException(
					"Invalid amount entered. \n Kindly enter valid amount in the multiples of 10");
		}

		if (withdrawlAmount > account.getBalance()) {
			throw new InsufficientBalanceException("Insufficient balance in account");
		}
	}

	private void dispenseAmount() {
		double tempAmount = withdrawlAmount;
		dispensedNotes = new TreeMap<Integer, Integer>(Collections.reverseOrder());
		for (int i = 0; i < possibleNotes.size() && tempAmount > 9; i++) {
			int noteCount = (int) (tempAmount / possibleNotes.get(i));
			tempAmount = (tempAmount % possibleNotes.get(i));

			if (noteCount > 0) {
				dispensedNotes.put(possibleNotes.get(i), noteCount);
			}

		}
	}

	private void printMessage() {
		synchronized (AccountOperations.class) {
			if (null == errorMessage) {
				System.out.println(account.getName() + " your withdrawl is successfull!!");
				System.out.println("Denominated Notes for withdrawl amount "+withdrawlAmount+" :: ");
				for (Map.Entry<Integer, Integer> dn : dispensedNotes.entrySet()) {
					System.out.println(dn.getKey() + " : " + dn.getValue());
				}
				System.out.println("Remaining balance for account# " + account.getAccNumber() + " :: "
						+ account.getBalance() + "\n");
			} else {
				System.out.println(
						account.getName() + " your withdrawl can not be processed due to :: " + errorMessage + "\n");
			}

		}
	}

	@Override
	public void run() {
		cashwithdrawl();
	}

}
