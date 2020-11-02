package com.in28minutes.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RestWebServiceDemoApplication  {	
	
	public static void main(String[] args) {
		
		 new SpringApplicationBuilder(RestWebServiceDemoApplication.class)
         .run(args);
	}


	

}
