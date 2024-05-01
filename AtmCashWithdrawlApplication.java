package com.citibank.atm.cashwithdrawl;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.citibank.atm.cashwithdrawl.pojos.Account;
import com.citibank.atm.cashwithdrawl.service.AccountOperations;


/*
 * Main class to test the functionality of AccountOperations class 
 * Here i have created the threadpool and called cashwithdrawl through the threads.
 */
public class AtmCashWithdrawlApplication {

	public static void main(String[] args) {
		
		Scanner inputTaker = new Scanner(System.in);
		
		
		// initializeAccountData
		Account account1 = new Account("Test1", 12345, 10000);
		Account account2 = new Account("Test2", 23456, 5000);
		Account account3 = new Account("Test3", 34567, 2345.0);
		
		//Asking user input for withdrawl amounts
		System.out.println("Hello "+account1.getName()+" Enter your withdrawl amount : ");
		double withdrawl1 = inputTaker.nextDouble();
		System.out.println("Hello "+account2.getName()+" Enter your withdrawl amount : ");
		double withdrawl2 = inputTaker.nextDouble();
		System.out.println("Hello "+account3.getName()+" Enter your withdrawl amount : ");
		double withdrawl3 = inputTaker.nextDouble();

		//passing data to executor service
		ExecutorService withdrawlExecutor = Executors.newCachedThreadPool();
		
		withdrawlExecutor.execute(new AccountOperations(account1, withdrawl1));
		withdrawlExecutor.execute(new AccountOperations(account2, withdrawl2));
		withdrawlExecutor.execute(new AccountOperations(account3, withdrawl3));
		
		
		withdrawlExecutor.shutdown();
	}

}
