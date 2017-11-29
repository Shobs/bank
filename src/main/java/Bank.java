package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Bank {
	BlockingQueue<Transaction> queue;
	private final Transaction nullTrans = new Transaction(-1, 0, 0);
	Account[] accounts = new Account[20];
	public Bank() {
		queue = new LinkedBlockingQueue();
		for(int i = 0; i < accounts.length ; i++)
		{
			accounts[i] = new Account(i);
		}
	}

	// TODO: Add code for actually updating accounts.
	// Should you make this synchronized? Why or why not?
	// If not, how do you avoid concurrency issues?
	public void processTransaction(Transaction t) {
		Account fromAccount = accounts[t.fromAccount];
		fromAccount.setNumberOfTransaction(fromAccount.getNumberOfTransactions()+1);
		int amount = t.amount;
		fromAccount.setCurrentBalance(fromAccount.getCurrentBalance()-amount);
		Account toAccount = accounts[t.toAccount];
		toAccount.setNumberOfTransaction(toAccount.getNumberOfTransactions() + 1);
		toAccount.setCurrentBalance(toAccount.getCurrentBalance()+ t.amount);
//		System.out.println(t.toString());
//		
		
//		System.out.println("Updating account with transaction");
	}

	private class Worker extends Thread {
		public void run() {
			try {
				Transaction t;
				do {
					t = queue.take();
					if(t.fromAccount != -1)
					{
						processTransaction(t);
						System.out.println(this.getName() + t);
					}
					
				} while (t.fromAccount != -1);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			System.out.println(this.getName() + "exiting");
		}
	}
	
	private void printAccounts()
	{
		for(Account a : accounts)
		{
			System.out.println(a.toString());
		}
		
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		
		File fileD = new File(System.getProperty("user.dir"),"main\\java\\"+args[0]);
		int number = Integer.parseInt(args[1]);
		

	Bank bank = new Bank();

	// TODO: Replace the following with code to generate as
	// many workers as needed. The number of workers is
	// Given as a commandline argument.
	ArrayList<Worker> workers = new ArrayList<Worker>();
	for(int i = 0; i < number ; i++)
	{
		workers.add(bank.new Worker());
	}
	
	try
	{
		for(int i = 0; i < number; i++)
		{
			workers.get(i).start();
		}
		

		// TODO: replace the following with code for
		// reading from the file and putting the transactions
		// into the BlockingQueue
		try (BufferedReader br = new BufferedReader(new FileReader(fileD))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] strings = line.split(" ");
				bank.queue.put(new Transaction(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]),Integer.parseInt(strings[2])));
			}
		}

		
		for(int i = 0; i < number ; i++)
		{
			bank.queue.put(bank.nullTrans);
		}
//		bank.queue.put(bank.nullTrans);
//		bank.queue.put(bank.nullTrans);
//		System.out.println("Main finished adding all transactions");
		// TODO: Add code here to wait for ALL the workers to finish
		for(int i = 0; i < number ; i++)
		{
			workers.get(i).join();
		}
		
	}catch(
	InterruptedException e)
	{
		System.out.println("interrupted");
	}System.out.println("All threads done");
	
	bank.printAccounts();

}}


class Transaction {
	int fromAccount;
	int toAccount;
	int amount;

	public Transaction(int from, int to, int amt) {
		fromAccount = from;
		toAccount = to;
		amount = amt;
	}

	public String toString() {
		return "Transaction: from = " + fromAccount + ", to = " + toAccount + " amount = " + amount;
	}

}

// TODO: Create an Account class with id, num of transactions and account
// balance