package com.in28minutes.rest.entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private String firstName;
	private Integer id;
	private Date dob;
	
	
	
	private List<Post> posts;

	public User( String firstName, Integer id,
			Date dob) {
		super();
		this.firstName = firstName;
		this.id = id;
		this.dob = dob;
	}
	
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", id=" + id + ", dob=" + dob + "]";
	}

	
	
	
}
