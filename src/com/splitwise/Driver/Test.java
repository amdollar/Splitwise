package com.splitwise.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.splitwise.Model.Expences;
import com.splitwise.Model.User;
import com.splitwise.Service.Helper;

public class Test {
	Helper helper = new Helper();

	public static void main(String[] args) {
		Test test = new Test();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String cmd = sc.nextLine();
			test.performOperation(cmd);
		}
		sc.close();
	}

	private void performOperation(String cmd) {
		if (cmd.equals("SHOW")) {
			List<User> allusers = helper.getListOfUsers();
			allusers.stream().forEach(s -> printStatementForUser(s));
		} else if (cmd.startsWith("SHOW") && cmd.length() >= 4) {
			User user = helper.getUserById(cmd.split(" ")[1]);
			printStatementForUser(user);
			printStatementForThisUser(user.getUserid());
		} else if (cmd.startsWith("EXPENSE")) {
			saveExpences(cmd);
		} else if (cmd.equals("EXIT")) {
			System.exit(1);
		}

	}

	/*
	 * This method is for printing the statement for one user.
	 */

	private void printStatementForThisUser(String userid) {
		List<User> users = helper.getListOfUsers();
		for (User user : users) {
			Map<User, Integer> statement = user.getExpence().getStatement();
			if (statement.containsKey(userid)) {
				System.out.println(user.getName() + " owes " + userid + ":" + statement.get(userid));
			}
		}
	}

	private void printStatementForUser(User user) {
		if (user.getExpence().getStatement() != null) {
			for (Map.Entry<User, Integer> temp : user.getExpence().getStatement().entrySet()) {
				System.out.println(user.getName() + " owes " + temp.getKey() + ":" + temp.getValue());
			}
		} else {
			System.out.println("No Balance");
		}
	}

	/*
	 * This method is to save expenses, further divided into type of operations;
	 */
	private void saveExpences(String cmd) {
		String[] commands = cmd.split(" ");
		String operationType = helper.recognizeOperation(cmd);
//		String cmddemo = "EXPENSE <user-id-of-person-who-paid> <total-paid-amount> <no-of-users> <space-separated-list-of-users> <EQUAL/EXACT/PERCENT> <space-separated-values-in-case-of-non-equal>";
		User paidBy = helper.getUserById(commands[1]);
		int paidAmount = Integer.valueOf(commands[2]);
		int totalparts = Integer.valueOf(commands[3]);
		List<User> usersInvolved = new ArrayList<User>();
		extractUsersInvolvedInOp(cmd, commands, operationType, usersInvolved);
		if (operationType.equals("EQUAL")) {
			storeExpenSesInEqualOperation(paidBy, paidAmount, usersInvolved);
		}

	}

	/*
	 * This method is to store expenses for each user when the operation is "EQUAL"
	 */
	private void storeExpenSesInEqualOperation(User paidBy, int paidAmount, List<User> usersInvolved) {

		Map<User, Integer> payingUserexp = paidBy.getExpence().getStatement();
		if (payingUserexp.isEmpty()) {
			for (User user : usersInvolved) {
				if (!user.getUserid().equals(paidBy)) {
					Map<User, Integer> currentexpences = user.getExpence().getStatement();
					currentexpences.put(paidBy,
							currentexpences.getOrDefault(paidBy, 0) + paidAmount / usersInvolved.size());
					Expences expence = new Expences(paidBy, paidAmount / usersInvolved.size(), currentexpences);
					user.setExpence(expence);
				}
			}
		} else {
			for (Map.Entry<User, Integer> temp : payingUserexp.entrySet()) { // u4 = 20
				for (User user : usersInvolved) {
					if (temp.getKey().equals(user.getUserid())) {
						int mybarrow = temp.getValue();
						// update the user's expenses
						int tobeupdated = mybarrow - paidAmount;
						if (tobeupdated > 0) {
							// update my sheet
							temp.setValue(tobeupdated);
						} else {
							user.getExpence().getStatement().put(temp.getKey(), tobeupdated);
							// update user's sheet.
						}
					} else {
						Map<User, Integer> currentexpences = user.getExpence().getStatement();
						currentexpences.put(paidBy,
								currentexpences.getOrDefault(paidBy, 0) + paidAmount / usersInvolved.size());
						Expences expence = new Expences(paidBy, paidAmount / usersInvolved.size(), currentexpences);
						user.setExpence(expence);
					}
				}
			}
		}
	}

	// getting the list of users involved in one operation from the commands
	private void extractUsersInvolvedInOp(String cmd, String[] commands, String operationType,
			List<User> usersInvolved) {
		for (int i = 4; i < commands.length - 1; i++) {
			User tempuser = helper.getUserById(commands[i]);
			usersInvolved.add(tempuser);
		}
	}

}
