package com.rajkumar.apiigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Lazy;

import com.rajkumar.apiigateway.ratelimit.interceptor.RateLimitInterceptor;

@SpringBootApplication
@EnableZuulProxy
public class ApiiGatewayApplication {

	@Autowired
	@Lazy
	RateLimitInterceptor rateLimitInterceptor;
	

	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ApiiGatewayApplication.class)
					.run(args);
	}

}
