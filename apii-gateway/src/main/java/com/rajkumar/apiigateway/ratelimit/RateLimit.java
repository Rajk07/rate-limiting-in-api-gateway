package com.rajkumar.apiigateway.ratelimit;

import java.time.Duration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

public enum RateLimit {

	FREE(2L),
	BASIC(4L),
	PROFESSIONAL(10L);
	
	private Long tokens;
	
	private RateLimit(Long tokens) {
		this.tokens = tokens;
	}
	 public static RateLimit resolvePlanFromApiKey(String apiKey) {
		if(apiKey==null || apiKey.isEmpty()) {
			return FREE;
		}
		else if(apiKey.startsWith("BAS-")) {
			return BASIC;
		}
		else if(apiKey.startsWith("PRO-")) {
			return PROFESSIONAL;
		}
		return FREE;
	}
	
	 public Bandwidth getLimit() {
			return Bandwidth.classic(tokens, Refill.intervally(tokens, Duration.ofMinutes(1)));
	 }
}
