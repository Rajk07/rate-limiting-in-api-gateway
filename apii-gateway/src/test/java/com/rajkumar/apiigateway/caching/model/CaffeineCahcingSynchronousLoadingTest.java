package com.rajkumar.apiigateway.caching.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

class CaffeineCahcingSynchronousLoadingTest {

	LoadingCache<String, DataObject> cache;
	
	@BeforeEach
	void setup() {
		cache = Caffeine.newBuilder()
				.expireAfterWrite(1, TimeUnit.MINUTES)
				.build(k-> DataObject.get("Data for "+k));
	}
	
	@Test
	void test_get() {
		DataObject data = cache.get("A");
		assertNotNull(data);
		Executors.newScheduledThreadPool(1)
			.schedule(()-> {assertNull(data);  }, 1, TimeUnit.MINUTES);
	}
	
	@Test
	void test_getAll() {
		Map<String, DataObject> dataObjectMap = cache.getAll(Arrays.asList("A", "B", "C"));
		assertEquals(3,dataObjectMap.size());
		Executors.newScheduledThreadPool(1)
		.schedule(()-> {assertEquals(0,dataObjectMap.size());  }, 1, TimeUnit.MINUTES);
	}

}
