package com.citibank.atm.cashwithdrawl.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.citibank.atm.cashwithdrawl.exceptions.InvalidAmountException;
import com.citibank.atm.cashwithdrawl.exceptions.InsufficientBalanceException;
import com.citibank.atm.cashwithdrawl.pojos.Account;


/*
 * This class is used to perform cashwithdrawl functionality from atm for multiple 
 * customers simulateneously without interuppting each other's operation.
 * This class is also responsible for throwing user defined exceptions InsufficeinetBalance and InvalidAmount.
 * Both exceptions are being thrown from validate method.
 */
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

	/*
	 * This method is responsible for cash withdrawl
	 * It internally calls for validatewithdrawl , dispenseAmount and printMessage
	 */
	public void cashwithdrawl() {
		try {
			validateWithdrawl();
			
			synchronized (this) {
				double remainingAmount = account.getBalance() - withdrawlAmount;
				account.setBalance(remainingAmount);
				dispenseAmount();
			}
		} catch (InvalidAmountException | InsufficientBalanceException e) {
			errorMessage = e.getMessage();
		}
		
		printMessage();

	}

	/*
	 * this method is to validate below 2 points
	 * 1. amount entered by user is greater than 0 and should be in the multiples of 10
	 * 2. withdrawl amount should be less than or equal to the amount in the account balance.
	 */
	private void validateWithdrawl() throws InvalidAmountException, InsufficientBalanceException {
		if (withdrawlAmount <= 0) {
			throw new InvalidAmountException(
					"Invalid amount entered. \n Kindly enter amount greater than 0 and in multiples of 10");
		}
		
		if (withdrawlAmount % 10 != 0) {
			throw new InvalidAmountException(
					"Invalid amount entered. \n Kindly enter valid amount in the multiples of 10");
		}

		if (withdrawlAmount > account.getBalance()) {
			throw new InsufficientBalanceException("Insufficient balance in account");
		}
	}

	/*
	 * This method will gurantee the minimum no of notes to be dispensed.
	 * Also will execute if the validation is successful
	 */
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

	/*
	 * This will ensure to print the status of withdrawl.
	 * In case of success along with success message will print remaining amount in balance and
	 *  dispensed notes and their count along with no of notes
	 * In case of failure will print the cause of unsuccessful withdrawl
	 */
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
