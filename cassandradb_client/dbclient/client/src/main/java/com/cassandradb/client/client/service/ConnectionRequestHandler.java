package com.cassandradb.client.client.service;

/**
 * The DB Connection provides support for basic operations towards the C* database.
 * <br/>
 * This interface defines the methods to store data in, and get data from C*.
 */
public interface ConnectionRequestHandler {
	
	void insert(); 
	void delete();
	void insertBatch();
	void get();
	void getAll();

}
