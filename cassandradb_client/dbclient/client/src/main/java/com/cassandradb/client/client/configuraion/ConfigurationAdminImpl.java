package com.cassandradb.client.client.configuraion;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cassandradb.client.client.persistence.requesthandler.DatabaseIfHandler;
import com.cassandradb.client.client.service.configuration.ConfigurationAdmin;
import com.cassandradb.client.client.service.exceptions.ConfigNotFoundException;
import com.cassandradb.client.client.service.exceptions.DatabaseAlreadyExistsException;
import com.cassandradb.client.client.service.exceptions.InvalidRequestException;
import com.cassandradb.client.client.service.exceptions.ProcessingException;
import com.cassandradb.client.client.service.exceptions.TableAlreadyExistsException;

/**
 * This class handles the configuration of the storage.
 *
 */
@Component("configurationAdmin")
public class ConfigurationAdminImpl implements ConfigurationAdmin
{
    @Autowired
    private DatabaseIfHandler myDatabaseIfHandler;

    @Override
    public void createDatabase(DatabaseConfig databaseConfig)
        throws InvalidRequestException, DatabaseAlreadyExistsException,
        ProcessingException
    {
	myDatabaseIfHandler.createKeyspace(databaseConfig);
    }

    @Override
    public boolean createDatabaseIfNotExist(DatabaseConfig databaseConfig)
        throws InvalidRequestException, ProcessingException
    {
	return false;
    }

    @Override
    public void createTable(TableConfig tableConfig)
        throws InvalidRequestException, TableAlreadyExistsException,
        ProcessingException
    {

    }

    @Override
    public boolean createTableIfNotExist(TableConfig tableConfig)
        throws InvalidRequestException, ProcessingException
    {
	return false;
    }

    @Override
    public void updateDatabase(DatabaseConfig databaseConfig)
        throws InvalidRequestException, ConfigNotFoundException,
        ProcessingException
    {

    }

    @Override
    public void updateTable(TableConfig tableConfig)
        throws InvalidRequestException, ConfigNotFoundException,
        ProcessingException
    {

    }

    @Override
    public Map<String, DatabaseConfig> getAllDatabaseConfigurations()
        throws ProcessingException
    {
	return null;
    }

    @Override
    public Map<String, TableConfig> getAllTableConfigurations(
        String databaseName) throws InvalidRequestException, ProcessingException
    {
	return null;
    }

    @Override
    public List<String> getAllPartitions(DatabaseConfig databaseConfig)
        throws InvalidRequestException, ProcessingException
    {
	return null;
    }

}