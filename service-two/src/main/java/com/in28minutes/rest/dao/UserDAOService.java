package com.in28minutes.rest.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.in28minutes.rest.entity.User;

@Component
public class UserDAOService {
	public static List<User> usersList;
	public static int count;
	static {
		usersList = new ArrayList<User>();
		usersList.add(new User("Adam",1,new Date()));
		usersList.add(new User("Eve",2,new Date()));
		usersList.add(new User("Xander",3,new Date()));
		count = 3;
	}
	public List<User> getUsers() {		
		return usersList;
	}
	public void addUser(User user) {
		if(user.getId()==null) {
			user.setId(++count);
		}
		usersList.add(user);
	}
	public User getUser(int userId) {
		for(User iUser:usersList) {
			if(iUser.getId()==userId) {
				return iUser;
			}
		}
		return null;
	}
	
	
}
