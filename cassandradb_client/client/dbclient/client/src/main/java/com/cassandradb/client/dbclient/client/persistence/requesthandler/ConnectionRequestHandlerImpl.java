package com.cassandradb.client.dbclient.client.persistence.requesthandler;

import org.springframework.stereotype.Repository;

import com.cassandradb.client.dbclient.service.ConnectionRequestHandler;

@Repository("myConnectionRequestHandler")
public class ConnectionRequestHandlerImpl implements ConnectionRequestHandler {
    public ConnectionRequestHandlerImpl() {
    }

	@Override
	public void insert() {
		
	}

	@Override
	public void delete() {
		
	}

	@Override
	public void insertBatch() {
		
	}

	@Override
	public void get() {
		
	}

	@Override
	public void getAll() {
		
	}
    
}
