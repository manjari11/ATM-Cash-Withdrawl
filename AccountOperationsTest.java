package com.assignment.atmcashwithdrawl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.assignment.atmcashwithdrawl.exceptions.IncorrectAmountException;
import com.assignment.atmcashwithdrawl.exceptions.InsufficientBalanceException;
import com.assignment.atmcashwithdrawl.pojos.Account;

public class AccountOperationsTest {
	
	@Test
	public void testCashWithdrawlWhenWithdrawlSuccessful () throws IncorrectAmountException, InsufficientBalanceException {
		Account account = new Account("Test", 67567, 10000);
		AccountOperations withdrawlOperation = new AccountOperations(account, 4530);
		withdrawlOperation.cashwithdrawl();
		assertNull(withdrawlOperation.getErrorMessage());
		assertEquals(10000 - 4530, account.getBalance());
	}
	
	@Test
	public void testCashWithdrawlWhenInsufficientBalance() throws IncorrectAmountException, InsufficientBalanceException {
		Account account = new Account("Test", 6767, 1000);
		AccountOperations withdrawlOperation = new AccountOperations(account, 4530);
		withdrawlOperation.cashwithdrawl();
		assertNotNull(withdrawlOperation.getErrorMessage());
		assertTrue(withdrawlOperation.getErrorMessage().contains("Insufficient balance in account"));
		assertFalse(withdrawlOperation.getErrorMessage().contains("Invalid amount entered"));
	}
	
	@Test
	public void testCashWithdrawlWhenInvalidAmountEntered() throws IncorrectAmountException, InsufficientBalanceException {
		Account account = new Account("Test", 6767, 100000);
		AccountOperations withdrawlOperation = new AccountOperations(account, 3456);
		withdrawlOperation.cashwithdrawl();
		assertNotNull(withdrawlOperation.getErrorMessage());
		assertFalse(withdrawlOperation.getErrorMessage().contains("Insufficient balance in account"));
		assertTrue(withdrawlOperation.getErrorMessage().contains("Invalid amount entered"));
	}
}
