package com.rajkumar.apiigateway.caching.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;

class CaffeineCachingAsyncLoadingTest {

	private AsyncLoadingCache<String, DataObject> cache; 
	
	@BeforeEach
	void setup() {
		cache = Caffeine.newBuilder()
				.expireAfterWrite(10, TimeUnit.MINUTES)
				.buildAsync(k -> DataObject.get("Data for "+k));

	}
	
	@Test
	void test_get() {
		String key = "A";
		cache.get(key).thenAccept(dataObject -> {
			assertNotNull(dataObject);
			assertEquals("Data for "+key, dataObject.getData());
			Executors.newScheduledThreadPool(1)
			.schedule(()-> { assertNull(dataObject);}, 1, TimeUnit.SECONDS);
		});
	}

}
