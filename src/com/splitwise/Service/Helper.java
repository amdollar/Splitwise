package com.splitwise.Service;

import java.util.ArrayList;
import java.util.List;

import com.splitwise.Model.User;

public class Helper {

	User user1 = new User("u1", "anurag", 7880506965l, "anuragawasthi020@gmail.com");
	User user2 = new User("u2", "akshita", 7838108716l, "akshitakash96@gmail.com");
	User user3 = new User("u3", "ankita", 8636313209l, "ankitakash@gmail.com");
	User user4 = new User("u4", "anamika", 9455872087l, "anamikakash2012@gmail.com");

	// this method is to identify the operation type in the expences
	public String recognizeOperation(String cmd) {
		String op = "";
		if (cmd.contains("EQUAL")) {
			op = "EQUAL";
		} else if (cmd.contains("PERCENT")) {
			op = "PERCENT";
		} else if (cmd.contains("EXACT")) {
			op = "EXACT";
		}
		return op;
	}

	public User getUserById(String userid) {
		for (User temp : getListOfUsers()) {
			if (temp.getUserid().equals(userid)) {
				return temp;
			}
		}
		return null;
	}

	public List<User> getListOfUsers() {
		List<User> data = new ArrayList();
		data.add(user1);
		data.add(user2);
		data.add(user3);
		data.add(user4);
		return data;
	}
}
