package com.citibank.atm.cashwithdrawl.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.citibank.atm.cashwithdrawl.exceptions.InsufficientBalanceException;
import com.citibank.atm.cashwithdrawl.exceptions.InvalidAmountException;
import com.citibank.atm.cashwithdrawl.pojos.Account;
import com.citibank.atm.cashwithdrawl.service.AccountOperations;

/*
 * This class is to provide test cases for the AccountOperations class
 */
public class AccountOperationsTest {

	/*
	 * To test the successful scenario when withdrawl amount entered is in multiples
	 * of 10 and also withdrawl amount should be less tha equal to current balance
	 * in account
	 */
	@Test
	public void testCashWithdrawlWhenWithdrawlSuccessful() throws InvalidAmountException, InsufficientBalanceException {
		Account account = new Account("Test Case1", 67567, 10000);
		AccountOperations withdrawlOperation = new AccountOperations(account, 4530);
		withdrawlOperation.cashwithdrawl();
		assertNull(withdrawlOperation.getErrorMessage());
		assertEquals(10000 - 4530, account.getBalance());
	}

	/*
	 * To test negative scenario when user entering withdrawl amount that is greater
	 * that the current balance in the account
	 */
	@Test
	public void testCashWithdrawlWhenInsufficientBalance() throws InvalidAmountException, InsufficientBalanceException {
		Account account = new Account("Test Case2", 6767, 1000);
		AccountOperations withdrawlOperation = new AccountOperations(account, 4530);
		withdrawlOperation.cashwithdrawl();
		assertNotNull(withdrawlOperation.getErrorMessage());
		assertTrue(withdrawlOperation.getErrorMessage().contains("Insufficient balance in account"));
		assertFalse(withdrawlOperation.getErrorMessage().contains("Invalid amount entered"));
	}

	/*
	 * To test negative scenario when user entering withdrawl amount is not in
	 * multiples
	 */
	@Test
	public void testCashWithdrawlWhenInvalidAmountEntered()
			throws InvalidAmountException, InsufficientBalanceException {
		Account account = new Account("Test Case3", 6767, 100000);
		AccountOperations withdrawlOperation = new AccountOperations(account, 3456);
		withdrawlOperation.cashwithdrawl();
		assertNotNull(withdrawlOperation.getErrorMessage());
		assertFalse(withdrawlOperation.getErrorMessage().contains("Insufficient balance in account"));
		assertTrue(withdrawlOperation.getErrorMessage().contains("Invalid amount entered"));
	}

	/*
	 * To test negative scenario when user entering withdrawl amount is 0
	 */
	@Test
	public void testCashWithdrawlWhenAmountEnteredAsZero() throws InvalidAmountException, InsufficientBalanceException {
		Account account = new Account("Test Case4", 6767, 100000);
		AccountOperations withdrawlOperation = new AccountOperations(account, 0);
		withdrawlOperation.cashwithdrawl();
		assertNotNull(withdrawlOperation.getErrorMessage());
		assertFalse(withdrawlOperation.getErrorMessage().contains("Insufficient balance in account"));
		assertTrue(withdrawlOperation.getErrorMessage().contains("Invalid amount entered"));
	}

	/*
	 * To test negative scenario when user entering withdrawl amount is negative
	 */
	@Test
	public void testCashWithdrawlWhenAmountEnteredIsNegative()
			throws InvalidAmountException, InsufficientBalanceException {
		Account account = new Account("Test Case5", 6767, 100000);
		AccountOperations withdrawlOperation = new AccountOperations(account, -1234);
		withdrawlOperation.cashwithdrawl();
		assertNotNull(withdrawlOperation.getErrorMessage());
		assertFalse(withdrawlOperation.getErrorMessage().contains("Insufficient balance in account"));
		assertTrue(withdrawlOperation.getErrorMessage().contains("Invalid amount entered"));
	}
}
