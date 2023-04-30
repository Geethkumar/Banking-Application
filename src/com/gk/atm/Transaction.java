package com.gk.atm;

import java.util.Date;

public class Transaction {

	private double amount;    //transaction amount
	
	private Date timestamp;   //the transactions time and date
	
	private String memo;  // a memo for every transaction
	
	private Account inAccount;  // the transaction being performed which account
	
	/**
	 * create a new transaction 
	 * @param amount the amount transacted 
	 * @param inAccnt the account transaction belongs to 
	 */
	public Transaction(double amount, Account inAccnt) {
		this.amount=amount;
		this.inAccount=inAccnt;
		this.timestamp=new Date();
		this.memo="";
		
	}
	/**
	 * create a new transaction 
	 * @param amount the amount transacted 
	 * @param memo the memo for transaction 
	 * @param inAccnt  the account the transaction belongs to 
	 */
	public Transaction(double amount, String memo,Account inAccnt) {
		//we call two orguments constructor first
		this(amount,inAccnt);
		//set the memo 
		this.memo =memo;
		
	}
	public double getAmount() {
		return this.amount;
		
	}
	public String getSummaryLine() {
		if(this.amount>0) {
			return String.format("%s :$%.02f : %s",this.timestamp.toString(),this.amount,this.memo);
		}
		else {  
			return String.format("%s :$(%.02f) : %s",this.timestamp.toString(),-this.amount,this.memo);
		}
	}
}
