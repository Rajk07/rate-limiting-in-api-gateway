package com.rajkumar.apiigateway.ratelimit.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.rajkumar.apiigateway.ratelimit.RateLimit;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;

@Component
public class RateLimitService {
private LoadingCache<String, Bucket> cache = Caffeine.newBuilder()
													.expireAfterWrite(1, TimeUnit.MINUTES)
														.build(this::newBucket);
	
	public Bucket resolveBucket(String apiKey) {
		return cache.get(apiKey);
	}
	
	private Bucket newBucket(String apiKey) {
		RateLimit plan = RateLimit.resolvePlanFromApiKey(apiKey);
		Bucket bucket = Bucket4j.builder()	
						.addLimit(plan.getLimit())
						.build();
		return bucket;
	}
}
