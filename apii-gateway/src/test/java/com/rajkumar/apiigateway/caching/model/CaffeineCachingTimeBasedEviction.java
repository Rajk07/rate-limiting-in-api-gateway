package com.rajkumar.apiigateway.caching.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

class CaffeineCachingTimeBasedEviction {

	private Cache<String, DataObject> cache;
	private Cache<String,DataObject> cache1;
	
	private LoadingCache<String, DataObject> cache2;
	private LoadingCache<String, DataObject> cache3;
	
	private AsyncLoadingCache<String, DataObject> cache4;
	private AsyncLoadingCache<String, DataObject> cache5;
	
	@BeforeEach
	void setup() {
		cache = Caffeine.newBuilder()
					.expireAfterWrite(1, TimeUnit.MINUTES)
					.build();
		
		cache1 = Caffeine.newBuilder()
				.expireAfterAccess(1, TimeUnit.MINUTES)
				.build();
		
		cache2 = Caffeine.newBuilder()
					.expireAfterWrite(1, TimeUnit.MINUTES)
					.build(k -> DataObject.get("Data for "+k));
		cache3 = Caffeine.newBuilder()
				.expireAfterAccess(1, TimeUnit.MINUTES)
				.build(k -> DataObject.get("Data for "+k));
		
		cache4 = Caffeine.newBuilder()
					.expireAfterWrite(1,TimeUnit.MINUTES)
					.buildAsync(k-> DataObject.get("Data for "+k));
		cache5 = Caffeine.newBuilder()
				.expireAfterAccess(1,TimeUnit.MINUTES)
				.buildAsync(k-> DataObject.get("Data for "+k));

	}
	
	@Test
	void test_expireAfterWrite_manual() {
		cache.put("A", DataObject.get("Data for A"));
		Executors.newScheduledThreadPool(1).schedule(()->{cache.getIfPresent("A");}, 50, TimeUnit.SECONDS);
		Executors.newScheduledThreadPool(1).schedule(()-> {assertNull(cache.getIfPresent("A"));}, 1, TimeUnit.MINUTES);
	}
	
	@Test
	void test_expireAfterAccess_manual() {
		cache.put("A", DataObject.get("Data for A"));
		Executors.newScheduledThreadPool(1).schedule(()->{cache.getIfPresent("A");}, 50, TimeUnit.SECONDS);
		Executors.newScheduledThreadPool(1).schedule(()-> {assertNotNull(cache.getIfPresent("A"));}, 1, TimeUnit.MINUTES);
	}
	
	@Test
	void test_expireAfterWrite_synch() {
		DataObject data = cache2.get("A");
		Executors.newScheduledThreadPool(1).schedule(()-> {assertNull(data);}, 1, TimeUnit.MINUTES);
		Executors.newScheduledThreadPool(1).schedule(()-> {assertNotNull(cache2.get("A"));}, 1, TimeUnit.MINUTES);
	}
	
	@Test
	void test_expireAfterAccess_synch() {
		DataObject data = cache3.get("A");
		Executors.newScheduledThreadPool(1).schedule(()-> {assertNull(data);}, 1, TimeUnit.MINUTES);
		Executors.newScheduledThreadPool(1).schedule(()-> {assertNotNull(cache3.get("A"));}, 1, TimeUnit.MINUTES);
	}
	
	@Test
	void test_expireAfterWrite_async() {
		cache4.get("A").thenAccept(dataObject -> {assertNotNull(dataObject);
			Executors.newScheduledThreadPool(1).schedule(()->{assertNull(dataObject);}, 1, TimeUnit.MINUTES);
		});
	}
	
	@Test
	void test_expireAfterAccess_async() {
		cache5.get("A").thenAccept(dataObject -> {assertNotNull(dataObject);
			Executors.newScheduledThreadPool(1).schedule(()->{assertNull(dataObject);}, 1, TimeUnit.MINUTES);
		});
	}
}
