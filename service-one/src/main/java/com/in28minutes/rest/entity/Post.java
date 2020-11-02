package com.in28minutes.rest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Post {
	private int id;
	private String description;
	
	@JsonIgnore
	private User user;
	
	public Post(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}
	
	
}
