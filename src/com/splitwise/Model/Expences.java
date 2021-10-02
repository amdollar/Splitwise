package com.splitwise.Model;

import java.util.HashMap;
import java.util.Map;

public class Expences {
	private User payedBy;
	private int money;
	private Map<User, Integer> statement = new HashMap();

	public Map<User, Integer> getStatement() {
		return statement;
	}

	public void setStatement(Map<User, Integer> statement) {
		this.statement = statement;
	}

	public User getOwesto() {
		return payedBy;
	}

	public void setOwesto(User owesto) {
		this.payedBy = owesto;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Expences() {
	}

	public Expences(User payedBy, int money, Map<User, Integer> statement) {
		this.payedBy = payedBy;
		this.money = money;
		this.statement = statement;
	}
}
