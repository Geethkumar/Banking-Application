package com.gk.atm;

import java.util.ArrayList;
import java.util.Random;

public class Bank {

	private String name;  //bank name
	
	private ArrayList<User>users;  //each bank has list of users
	private ArrayList<Account> accounts;  // each user has list of bank acnts
	
	/**create a new bank account with empty lists of users and accounts
	 * 
	 * @param name name of the bank
	 */
	
	public  Bank(String name) {
		this.name=name;
		this.users=new ArrayList<User>();
		this.accounts=new ArrayList<Account>();
		
		
	}
	/**
	 * generate new uId for user
	 * @return
	 */
	
	public String getNewUserUID() {
		//initialize 
		String uid;
		Random rng=new Random();
		int len=6;
	    boolean nonUnique;
		
		//continue  looping untill we get a unique id 
		do {
			uid="";	
			for(int i=0;i<len;i++) {
				uid+=((Integer)rng.nextInt(10)).toString();
			}
			nonUnique=false;
			
			//check its a unique or not 
			for(User u:this.users) {
				if(uid.compareTo(u.getUid())==0) {
					nonUnique=true;
					break;		
				}
			}
		}while(nonUnique);
		return uid;
	}
	
	
	public String getNewAccountUID() {
		String uid;
		Random rng=new Random();
		int len=10;
		boolean nonUnique;
		
		//continue  looping untill we get a unique id 
		do {
			uid="";
			
			for(int i=0;i<len;i++) {
				uid+=((Integer)rng.nextInt(10)).toString();
			}
			nonUnique=false;
			
			//check its a unique or not 
			for(Account a:this.accounts) {
				if(uid.compareTo(a.getUID())==0) {
					nonUnique=true;
					break;
					
				}
			}
			
		}while(nonUnique);
			
		
		return uid;
		
	}
	/**
	 * 
	 * @param firstName   new uers name 
	 * @param lastName
	 * @param pin  user oin 
	 * @return   new user object 
	 */
	
	
	public User addUser(String firstName,String lastName,String pin) {
		//create a new user object and add it to user list
		User newUser=new User(firstName,lastName,pin, this);
		this.users.add(newUser);
		
		//create a savings account for the user and add to user and to bank account lists 
		
		Account newAccount=new Account("savings",newUser,this);
		
		//adding the account  to holder and bank Lists 
				newUser.addAccount(newAccount);  //added account to holder
				this.accounts.add(newAccount);  //added account to bank
				
				return newUser;		
				
	}	
		//creating user login 
	  
		   public User userLogin(String userID,String pin) {
			
			//search through the users 
			for(User u:this.users) {
				//check user id is correct 
				if(u.getUid().compareTo(userID)==0 && u.validatePin(pin)) {
					return u;
				}
			}
			//if we havent found userId or pin is incorrect 
			return null;
		}


		public void addAccount(Account newAccount) {
			// TODO Auto-generated method stub
			
		}
		public String getName() {
			return this.name;
		}
	}
	

