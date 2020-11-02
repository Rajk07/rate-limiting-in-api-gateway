package com.rajkumar.apiigateway.caching.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class DataObject {

	private String data;
	
	private static int objectCounter = 0;
	private static final Logger logger = LoggerFactory.getLogger(DataObject.class);
	
	private DataObject(String data) {
		this.data = data;
	}
	
	public  static DataObject get(String data) {
		objectCounter++;
		logger.info("Initializing DataObject#{} with data {}", objectCounter, data);
		return new DataObject(data);
	}

	
	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return "DataObject [data=" + data + "]";
	}
	
	
}
