package com.assignment.atmcashwithdrawl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.assignment.atmcashwithdrawl.pojos.Account;
import com.assignment.atmcashwithdrawl.service.AccountOperations;



public class AtmCashWithdrawlApplication {

	public static void main(String[] args) {
		// initializeAccountData
		Account account1 = new Account("Test1", 12345, 10000);
		Account account2 = new Account("Test2", 23456, 5000);
		Account accoount3 = new Account("Test3", 34567, 2345.0);

		ExecutorService withdrawlExecutor = Executors.newFixedThreadPool(3);
		withdrawlExecutor.execute(new AccountOperations(account1, 3990));
		withdrawlExecutor.execute(new AccountOperations(account2, 880));
		withdrawlExecutor.execute(new AccountOperations(accoount3, 1220));
		
		
		withdrawlExecutor.shutdown();
	}

}
