package com.cassandradb.client.client.configuraion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.cassandradb.client.client.configuraion.DbConfigInternal.DbConfigInternalBuilder;
import com.cassandradb.client.client.persistence.requesthandler.DatabaseIfHandler;
import com.cassandradb.client.client.service.configuration.ConfigurationAdmin;
import com.cassandradb.client.client.service.configuration.DatabaseConfig;
import com.cassandradb.client.client.service.exceptions.ConfigNotFoundException;
import com.cassandradb.client.client.service.exceptions.DatabaseAlreadyExistsException;
import com.cassandradb.client.client.service.exceptions.DbServiceException;
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
    private final String DB_GLOBAL = "_global";
    private final String DB_PARTITIONED = "_partition__";
    private final String defaultPartitionName = "1";
    private Map<String, String> defaultReplication = new HashMap<>(1);

    @Autowired
    private DatabaseIfHandler myDatabaseIfHandler;

    public ConfigurationAdminImpl()
    {
	defaultReplication.put("india_dc", "1");
    }

    @Override
    public void createDatabase(DatabaseConfig databaseConfig)
        throws InvalidRequestException, DatabaseAlreadyExistsException,
        ProcessingException, DbServiceException
    {
	if (databaseConfig == null)
	{
	    throw new InvalidRequestException("DatabaseConfig can not be null");
	}

	DbConfigInternal internalDbConfigObj = getPersistenceDatabaseConfig(
	    databaseConfig);

	myDatabaseIfHandler.createKeyspace(internalDbConfigObj);
    }

    @Override
    public boolean createDatabaseIfNotExist(DatabaseConfig databaseConfig)
        throws InvalidRequestException, ProcessingException, DbServiceException
    {
	if (databaseConfig == null)
	{
	    throw new InvalidRequestException("DatabaseConfig can not be null");
	}

	DbConfigInternal internalDbConfigObj = getPersistenceDatabaseConfig(
	    databaseConfig);
	return myDatabaseIfHandler
	    .createKeyspaceIfNotExists(internalDbConfigObj);
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

    private DbConfigInternal getPersistenceDatabaseConfig(
        DatabaseConfig databaseConfig)
    {
	Map<String, String> replicationFactorInReq = databaseConfig
	    .getReplication();
	if (CollectionUtils.isEmpty(replicationFactorInReq))
	{
	    replicationFactorInReq = defaultReplication;
	}
	DbConfigInternalBuilder builder = DbConfigInternal.newBuilder();
	if (databaseConfig.getPartitionType() == PartitionType.GLOBAL)
	{
	    builder.setDatabaseName(
	        databaseConfig.getDatabaseName().concat(DB_GLOBAL));
	    builder.setPartitionType(databaseConfig.getPartitionType());
	    builder.setReplication(replicationFactorInReq);
	} else if (databaseConfig
	    .getPartitionType() == PartitionType.PARTITIONED)
	{
	    builder.setDatabaseName(
	        databaseConfig.getDatabaseName().concat(DB_PARTITIONED)
	            .concat(databaseConfig.getPartition() == null
	                ? defaultPartitionName
	                : databaseConfig.getPartition()));
	    builder.setPartitionType(databaseConfig.getPartitionType());
	    builder.setReplication(replicationFactorInReq);
	}
	return builder.build();
    }

}
