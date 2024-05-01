package com.citibank.atm.cashwithdrawl.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.citibank.atm.cashwithdrawl.exceptions.IncorrectAmountException;
import com.citibank.atm.cashwithdrawl.exceptions.InsufficientBalanceException;
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
	public void testCashWithdrawlWhenWithdrawlSuccessful()
			throws IncorrectAmountException, InsufficientBalanceException {
		Account account = new Account("Test", 67567, 10000);
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
	public void testCashWithdrawlWhenInsufficientBalance()
			throws IncorrectAmountException, InsufficientBalanceException {
		Account account = new Account("Test", 6767, 1000);
		AccountOperations withdrawlOperation = new AccountOperations(account, 4530);
		withdrawlOperation.cashwithdrawl();
		assertNotNull(withdrawlOperation.getErrorMessage());
		assertTrue(withdrawlOperation.getErrorMessage().contains("Insufficient balance in account"));
		assertFalse(withdrawlOperation.getErrorMessage().contains("Invalid amount entered"));
	}

	/*
	 * To test negative scenario when user entering withdrawl amount is either not in multiples
	 * of 10 or is less than equal to 0
	 */
	@Test
	public void testCashWithdrawlWhenInvalidAmountEntered()
			throws IncorrectAmountException, InsufficientBalanceException {
		Account account = new Account("Test", 6767, 100000);
		AccountOperations withdrawlOperation = new AccountOperations(account, 3456);
		withdrawlOperation.cashwithdrawl();
		assertNotNull(withdrawlOperation.getErrorMessage());
		assertFalse(withdrawlOperation.getErrorMessage().contains("Insufficient balance in account"));
		assertTrue(withdrawlOperation.getErrorMessage().contains("Invalid amount entered"));
	}
}
