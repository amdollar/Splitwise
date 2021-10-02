package com.splitwise.Model;

public class User {
	private String userid;
	private String name;
	private long mobileno;
	private String email;

	private Balance balance;
	private Expences expence;

	public User() {
	}

	public User(String userid, String name, long mobileno, String email) {
		this.userid = userid;
		this.name = name;
		this.mobileno = mobileno;
		this.email = email;
		this.balance = new Balance(0);
		this.expence = new Expences();

	}

	public int getBalance() {
		return balance.getBalance();
	}

	public void setBalance(Balance balance) {
		this.balance = balance;
	}

	public Expences getExpence() {
		return expence;
	}

	public void setExpence(Expences expence) {
		this.expence = expence;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getMobileno() {
		return mobileno;
	}

	public void setMobileno(long mobileno) {
		this.mobileno = mobileno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
