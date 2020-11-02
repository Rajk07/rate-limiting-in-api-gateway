package com.rajkumar.apiigateway.caching.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

class CaffeineCachingSizeBasedEvictionTest {

	LoadingCache<String, DataObject> cache;
	LoadingCache<String, DataObject> weightCache;
	
	@BeforeEach
	void setup() {
		cache = Caffeine.newBuilder()
					.maximumSize(1)
					.build(k -> DataObject.get("Data for "+ k));
		
		weightCache = Caffeine.newBuilder()
						.maximumWeight(10)
							.weigher((k,v)-> 5)
							.build(k -> DataObject.get("Data for "+k));
	}
	
	
	@Test
	void test_maximumSize() {
		assertEquals(0,cache.estimatedSize());
		cache.get("A");
		assertEquals(1, cache.estimatedSize());
		cache.get("B");
		cache.cleanUp();
		assertEquals(1, cache.estimatedSize());
	}
	
	@Test
	void test_maximumWeight() {
		assertEquals(0,cache.estimatedSize());
		cache.get("A");
		assertEquals(1,cache.estimatedSize());

		cache.get("B");
		assertEquals(2,cache.estimatedSize());

		cache.get("C");
		cache.cleanUp();
		assertEquals(2, cache.estimatedSize()); // test failing here, donno y
	}

}
