package com.rajkumar.apiigateway.caching.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@SpringBootTest
class CaffieneCachingManualLoadingTest {
   Cache<String, DataObject> cache;
			
	@BeforeEach
	void  setup() {
		 cache = Caffeine.newBuilder()
				.expireAfterWrite(1,TimeUnit.MINUTES)
				.maximumSize(100)
				.build();
	}
	
	@Test
	void test1_getIfPresent() {
		DataObject data = cache.getIfPresent("A");
		assertNull(data);
	}
	
	@Test
	void test2_put() {
		cache.put("A", DataObject.get("Data for A"));
		DataObject data = cache.getIfPresent("A");
		assertNotNull(data);
		Executors.newScheduledThreadPool(1).schedule(()-> { assertNull(data);}, 1, TimeUnit.MINUTES);
		}
	
	@Test
	void test3_get() {
		DataObject data = cache.get("A", (k) -> { return DataObject.get(k);});
		assertNotNull(data);
		Executors.newScheduledThreadPool(1).schedule(()-> { assertNull(data);}, 1, TimeUnit.MINUTES);
	}

	
}
