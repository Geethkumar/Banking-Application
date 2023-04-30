package com.gk.atm;

import java.util.ArrayList;

public class Account {

	private String name; //name of the account
	private double balance;    //current balance
	private String uid;     // account number
	
	private User holder;   //the user that owns this account
	
	private ArrayList<Transaction> transactions;   //account has list of transactions
	
	/** create new account 
	 * 
	 * @param name    name of the bank account
	 * @param holder    name of bank holder
	 * @param theBank    bank that issues the acccount
	 */
	
	public Account(String name,User holder,Bank theBank) {
		//set the account name and holder
		this.name=name;
		this.holder=holder;
		
		//get new acount uid
		this.uid=theBank.getNewUserUID();
		
		//intialize transactions
		this.transactions=new ArrayList<Transaction>();
		
		
		
		
	}
	public String getUID() {
		return this.uid; 
	}
	
	public String getSummaryLine() {
		//get accounts balance 
		double balance=this.getBalance();
		
		//format summary line depending on balance 
		if(balance >=0) {
			return String.format("%s : $%.02f : %s ",this.uid,balance,this.name); 
		}
		else {
			return String.format("%s : $(%.02f) ",this.uid,balance,this.name);
		}
	}
	
	public double getBalance() {
		double balance=0;
	for(Transaction t:this.transactions) {
		balance+=t.getAmount();
		 
	}
	return balance;
	}
	
	public void printTransHistory() {
		System.out.printf("\nTransaction history for Account %s\n",this.uid);
		
		for(int t=this.transactions.size()-1;t>=0;t--) {
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
	}
	public void addTransaction(double amount,String memo) {
		//create new transaction object and add it to our lists
		Transaction newTransaction =new Transaction(amount,memo,this);
		this.transactions.add(newTransaction);
		
	}
}
