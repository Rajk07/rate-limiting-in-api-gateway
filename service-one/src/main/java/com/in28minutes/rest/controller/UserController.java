package com.in28minutes.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.rest.dao.UserDAOService;
import com.in28minutes.rest.entity.User;

@RestController
@RequestMapping(value ="/throttling")
public class UserController {

	@Autowired
	private UserDAOService service;
	
	
	
	
	@GetMapping("/users")
	public ResponseEntity<Object> getUsers() {
			List<User> usersList = service.getUsers();
			return new ResponseEntity(usersList, HttpStatus.OK);
	}

}
