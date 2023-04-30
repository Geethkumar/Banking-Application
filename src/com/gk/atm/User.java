package com.gk.atm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

	private String firstName;
	private String lastName;
	private String uid;
	private byte pinHash[];
	
	private ArrayList<Account> accounts;    //----> user can have multiple accounts so we use arrayList to store.
	
	public User(String firstName, String lastName, String pin,Bank theBank) {
		//set users name
		this.firstName=firstName;  //same as to the property name
		this.lastName=lastName;
		
		//store the MD5 has pin rather than storing original value for security reasons
		
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			this.pinHash=md.digest(pin.getBytes()); //getting pin byte objects and digesting it into MD5 algorithm.
			
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error, no suchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		
		//get a new, unique id for user 
		this.uid=theBank.getNewUserUID();
		
		//create empty list of accounts
		this.accounts = new ArrayList<Account>();
		
		/** @param firstName
		 * @param lastName
		 * @param pin
		 * @param theBank
		 */
		
		//print log message
		System.out.printf("New user%s, %s with id %s created.\n",lastName,firstName,this.uid);
	}
	
	/**add an account for the user
	 * 
	 * @param anAccnt
	 */
	
	public void addAccount(Account anAccnt) {
		this.accounts.add(anAccnt);
	}
	/**
	 * return user id   
	 * @return
	 */
	public String getUid() {
		return this.uid;
	}
	
	/** 
	 * 
	 * @param pin
	 * @return
	 */
	public boolean validatePin(String pin) {
		
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			
			System.err.println("error, no suchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}
	public String getFirstName() {
		return this.firstName; 
		
	}
	
	/**
	 * prints summary of accounts of this user
	 */
	
	public void printAccountsSummary() {
		System.out.printf("\n\n%s's accounts summary\n",this.firstName);
		
		for(int i=0;i<this.accounts.size();i++) {
			System.out.printf("%d) %s\n",i+1,this.accounts.get(i).getSummaryLine());  //here we get two bank accounts one for savings and another for checking account 
		}
		System.out.println();
	}
	
	public int numAccounts() {
		return this.accounts.size();
		
	}
	public void printAccountTransHistory(int acctIdx) {
	this.accounts.get(acctIdx).printTransHistory();
}
   
   public double getAcntBalance(int acctIdx) {
	   return this.accounts.get(acctIdx).getBalance();
   }
		public String getAccountUID(int acctIdx) {
			return this.accounts.get(acctIdx).getUID();
		}
		
		public void addAccountTransaction(int acctIdx,double amount, String memo) {
			this.accounts.get(acctIdx).addTransaction(amount,memo);
		}
	}
