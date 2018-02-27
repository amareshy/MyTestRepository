package com.frs.dataaccess.client.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.frs.dataaccess.client.service.Connection;
import com.frs.dataaccess.client.service.DBService;
import com.frs.dataaccess.client.service.async.AsyncConnection;
import com.frs.dataaccess.client.service.configuration.ConfigurationAdmin;
import com.frs.dataaccess.client.service.iterator.EntityIteratorFactory;
import com.frs.dataaccess.client.service.status.StatusAdmin;

public class DBServiceImpl implements DBService {
	private static final Logger LOG = LoggerFactory.getLogger(DBServiceImpl.class);

	public  DBServiceImpl() {
		
	}
	
	
	@Override
	public Connection getConnection() {
		return null;
	}

	@Override
	public AsyncConnection getAsyncConnection() {
		return null;
	}

	@Override
	public EntityIteratorFactory getEntityIteratorFactory() {
		return null;
	}

	@Override
	public StatusAdmin getStatusAdmin() {
		return null;
	}

	@Override
	public ConfigurationAdmin getConfigurationAdmin() {
		return null;
	}

}
