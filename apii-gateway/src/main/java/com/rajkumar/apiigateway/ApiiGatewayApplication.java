package com.rajkumar.apiigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.rajkumar.apiigateway.ratelimit.interceptor.RateLimitInterceptor;

@SpringBootApplication
@EnableZuulProxy
public class ApiiGatewayApplication implements WebMvcConfigurer{

	@Autowired
	//@Lazy
	RateLimitInterceptor rateLimitInterceptor;
	

	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ApiiGatewayApplication.class)
					.run(args);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(rateLimitInterceptor)
		.addPathPatterns("/api/service_1/throttling/users");
	}

}
