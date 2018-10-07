package com.cassandradb.client.dbclient.client.configuraion;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cassandradb.client.dbclient.service.configuration.ConfigurationAdmin;
import com.cassandradb.client.dbclient.service.exceptions.ConfigNotFoundException;
import com.cassandradb.client.dbclient.service.exceptions.DatabaseAlreadyExistsException;
import com.cassandradb.client.dbclient.service.exceptions.InvalidRequestException;
import com.cassandradb.client.dbclient.service.exceptions.ProcessingException;
import com.cassandradb.client.dbclient.service.exceptions.TableAlreadyExistsException;

@Component("configurationAdmin")
public class ConfigurationAdminImpl implements ConfigurationAdmin {

	@Override
	public void createDatabase(DatabaseConfig databaseConfig)
			throws InvalidRequestException, DatabaseAlreadyExistsException, ProcessingException {
		
	}

	@Override
	public boolean createDatabaseIfNotExist(DatabaseConfig databaseConfig)
			throws InvalidRequestException, ProcessingException {
		return false;
	}

	@Override
	public void createTable(TableConfig tableConfig)
			throws InvalidRequestException, TableAlreadyExistsException, ProcessingException {
		
	}

	@Override
	public boolean createTableIfNotExist(TableConfig tableConfig) throws InvalidRequestException, ProcessingException {
		return false;
	}

	@Override
	public void updateDatabase(DatabaseConfig databaseConfig)
			throws InvalidRequestException, ConfigNotFoundException, ProcessingException {
		
	}

	@Override
	public void updateTable(TableConfig tableConfig)
			throws InvalidRequestException, ConfigNotFoundException, ProcessingException {
		
	}

	@Override
	public Map<String, DatabaseConfig> getAllDatabaseConfigurations() throws ProcessingException {
		return null;
	}

	@Override
	public Map<String, TableConfig> getAllTableConfigurations(String databaseName)
			throws InvalidRequestException, ProcessingException {
		return null;
	}

	@Override
	public List<String> getAllPartitions(DatabaseConfig databaseConfig)
			throws InvalidRequestException, ProcessingException {
		return null;
	}

}
