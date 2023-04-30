package com.gk.atm;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
	
		Scanner sc=new Scanner(System.in);
		
		//intialize bank object 
		Bank theBank=new Bank("State bank of India");
		
		//add a user which also createes a savings account 
		User aUser=theBank.addUser("Geeth Kumar", "Katta", "1234");
		
		//add a checking account for our user
		Account newAccount=new Account("checking", aUser,theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while(true) {
			//stay in the login prompt until it gets successful login
			curUser =ATM.mainMenuPrompt(theBank,sc);
			
			//stay in main menu untill user quits   
			ATM.printUserMenu(curUser,sc);
			
		}
	}
	public static User mainMenuPrompt(Bank theBank,Scanner sc) {
		//initialize 
		String userId;
		String pin;
		User authUser;
		
		//prompt the user for user  id/pin combo until a correct one reached
		do {
			System.out.printf("\n\nwelcome to %s\n\n", theBank.getName());
			System.out.print("enter user id: ");
			userId=sc.nextLine();
			System.out.print("Enter pin :");
			pin=sc.nextLine();
			
			//try to get the user object corresponding to the  ID and Pin combo 
		   authUser=	theBank.userLogin(userId, pin);
		   if(authUser ==null) {
			    System.out.println("Incorrect user ID/pin  "+"please try again");
		   }
		   
		}while(authUser ==null);   //continue looping until successful 
		
		return authUser; 
	}
	
	public static void printUserMenu(User theUser, Scanner sc) {
		//print summary of user's account 
		
		theUser.printAccountsSummary();
		
		//initialixing 
		int choice;
		
		//user menu 
		do {
			System.out.printf("Welcome %s, what would you like to do?",theUser.getFirstName());
			System.out.println("1) show transaction history");
			System.out.println("2) withrawl");
			System.out.println("3) Deposite");
			System.out.println("4) Transfer");
			System.out.println("5) Quit");
			System.out.println();
			
			System.out.print("Enter Choice: ");
			choice=sc.nextInt();
			
			if(choice <1 || choice>5) {
				System.out.println("Invalid choice ...please choose 1-5");
			}
		}while(choice<1 || choice>5);
		
		switch(choice) {
		case 1:
			ATM.showTransHistory(theUser,sc);
			break;
		case 2: 
			ATM.withdrawlFunds(theUser,sc);
			break;
		case 3:
			ATM.depositeFunds(theUser,sc);
			break;
		case 4:
			ATM.transferFunds(theUser,sc);
			break;
			
		case 5:
			//gobble up rest of previous input
			sc.nextLine();
			break;
			
		}
		//redisplay the menu 
		if(choice !=5) {
			ATM.printUserMenu(theUser,sc);
			
		}
	}
	public static void showTransHistory(User theUser,Scanner sc) {
		int theAcct;
		
		//get the account whose transaction history to look at
		do {
			System.out.printf("Enter the number (1-%d) of ghe account"+" whose transaction you want to see :",
					theUser.numAccounts());
			
			theAcct=sc.nextInt()-1;
			
			if(theAcct <0 || theAcct>=theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again. ");
			}
		}while(theAcct<0 || theAcct>=theUser.numAccounts());
		
		//if the accciunt num is exixsts print the history
		
		theUser.printAccountTransHistory(theAcct);
	}
	
	public static void transferFunds(User theUser, Scanner sc) {
		//intializing variables for the amount which transferring from the account and to account 
		int fromAccount;
		int toAccount;
		double amount;
		double acntBalance;
		
		//get the account to transfer from 
		
		do {
			System.out.printf("Enter the number (1-%d) of the account \n"+" to transfer from",theUser.numAccounts());
			fromAccount=sc.nextInt()-1;
			
			if(fromAccount<0 ||fromAccount>=theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again. ");
			}
		}while(fromAccount>0 || fromAccount >=theUser.numAccounts());
		acntBalance=theUser.getAcntBalance(fromAccount);
		
		
		//get account to transfer to which the account 
		do {
			System.out.printf("Enter the number (1-%d) of the account \n"+" to transfer to",theUser.numAccounts());
			toAccount=sc.nextInt()-1;
			
			if(toAccount<0 ||toAccount>=theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again. ");
			}
		}while(toAccount<0 || toAccount >=theUser.numAccounts());
		
		//get amount to transfer 
		do {
			System.out.printf("Enter the amount to transfer (max $%.02f) :$", acntBalance);
			
			amount=sc.nextDouble();
			
			if(amount<0) {
				System.out.println("Amount must be greater than zero.");
			}
			else {
				System.out.printf("Amount must be greater than\n"+" balance of $%.02f.\n",acntBalance);
			}
		}while(amount <0 || amount>acntBalance);
		
		//finally do the transfer 
		theUser.addAccountTransaction(fromAccount, -1*amount,
				String.format("transfer to account %s", theUser.getAccountUID(toAccount)));
		
		theUser.addAccountTransaction(toAccount, -1*amount,
				String.format("transfer to account %s", theUser.getAccountUID(fromAccount)));
	}
	
	//withdrawl
	
	public static void withdrawlFunds(User theUser,Scanner sc) {
		
		        //intializing variables for the amount which transferring from the account and to account 
				int fromAccount;
				String memo; 
				double amount;
				double acntBalance;
				
				//get the account to transfer from 
				do {
					System.out.printf("Enter the number (1-%d) of the account \n"+" to withdraw from",theUser.numAccounts());
					fromAccount=sc.nextInt()-1;
					if(fromAccount<0 ||fromAccount>=theUser.numAccounts()) {
						System.out.println("Invalid account. Please try again. ");
					}
				}while(fromAccount>0 || fromAccount >=theUser.numAccounts());
				acntBalance=theUser.getAcntBalance(fromAccount);
				
				//get amount to transfer 
				do {
					System.out.printf("Enter the amount to withdraw  (max $%.02f) :$", acntBalance);
					
					amount=sc.nextDouble();
					
					if(amount<0) {
						System.out.println("Amount must be greater than zero.");
					}
					else if(amount>acntBalance){
						System.out.printf("Amount must not be greater than\n"+" balance of $%.02f.\n",acntBalance);
					}
				}while(amount <0 || amount>acntBalance);
				
				//gobble up rest of previous input 
				sc.nextLine();
				
				//get a memo 
				
				System.out.print("Enter a memo: ");
				memo=sc.nextLine();
				
				//do the draw 
				theUser.addAccountTransaction(fromAccount, -1*amount, memo);
	}
	
	/**
	 * Deposite funds processs
	 * @param theUser
	 * @param sc
	 */
	public static void depositeFunds(User theUser,Scanner sc) {
		
		 //intializing variables for the amount which transferring from the account and to account 
		int toAccount; 
		double amount;
		double acntBalance;
		String memo;
		
		//get the account to transfer from 
		do {
			System.out.printf("Enter the number (1-%d) of the account\n"+" to deposite in ",theUser.numAccounts());
			toAccount=sc.nextInt()-1;
			if(toAccount<0 ||toAccount>=theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again. ");
			}
		}while(toAccount>0 || toAccount >=theUser.numAccounts());
		acntBalance=theUser.getAcntBalance(toAccount);
		
		//get amount to transfer 
		do {
			System.out.printf("Enter the amount to deposite (max $%.02f) :$", acntBalance);
			
			amount=sc.nextDouble();
			
			if(amount<0) {
				System.out.println("Amount must be greater than zero.");
			}
		//	else {
				//System.out.printf("Amount must be greater than\n"+" balance of $%.02f.\n",acntBalance);
			// }
		}while(amount <0);
		
		//gobble up rest of previous input 
		sc.nextLine();
		
		//get a memo 
		
		System.out.print("Enter a memo: ");
		memo=sc.nextLine();
		
		//do the draw 
		theUser.addAccountTransaction(toAccount, amount, memo);
	}
}
