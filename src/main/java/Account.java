package main.java;

public class Account {
		private int id;
	   private int currentBalance;
	   private int numberOfTransaction;
	  
	   public Account(int id)
	   {
	       this.id = id;
	       this.currentBalance = 1000;
	       this.numberOfTransaction = 0;
	   }
	  
	   public int getCurrentBalance()
	   {
	       return currentBalance;
	   }
	   public void setCurrentBalance(int b)
	   {
		   currentBalance = b;
	   }
	  
	   public int getID()
	   {
	       return id;
	   }
	   
	   public void setID(int id)
	   {
		   this.id = id;
	   }
	  
	   public int getNumberOfTransactions()
	   {
	       return numberOfTransaction;
	   }
	   
	   public void setNumberOfTransaction(int number)
	   {
		   numberOfTransaction = number;
	   }
	   
//	   public void transfer(Account other, int amount)
//	   {
//	       this.balance = this.balance - amount;
//	       other.balance = other.balance + amount;
//	   }
	  
//	   public int addNumOfTrans(int amount)
//	   {
//	       return this.numoft = this.numoft + amount;
//	   }
	  
	   public String toString()
	   {
	       return "account:" + id + " Current Balance: " + currentBalance + " Transaction:" + 
	   numberOfTransaction;
	   }
	

}
