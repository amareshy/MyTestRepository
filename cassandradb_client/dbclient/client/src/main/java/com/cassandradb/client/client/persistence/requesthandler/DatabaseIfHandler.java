package com.cassandradb.client.client.persistence.requesthandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cassandradb.client.client.configuraion.DbConfigInternal;
import com.cassandradb.client.client.datasource.ClusterConnection;
import com.cassandradb.client.client.service.exceptions.AlreadyExistException;
import com.cassandradb.client.client.service.exceptions.DbServiceException;
import com.cassandradb.client.client.service.exceptions.UnableToProcessException;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.exceptions.AlreadyExistsException;

/**
 * This Class is responsible to create and modify databases and tables in
 * Cassandra.
 */
public class DatabaseIfHandler
{
    private static final Logger LOGGER = LoggerFactory
        .getLogger(DatabaseIfHandler.class);
    @Autowired
    private ClusterConnection myClusterConnection;
    private static final char QUOTE = '\'';
    private static final String REPLICATION_FORMAT = "%s%s%s:%s";

    public DatabaseIfHandler()
    {
    }

    public synchronized void createKeyspace(DbConfigInternal databaseConfig)
        throws DbServiceException
    {
	String keyspaceName = databaseConfig.getDatabaseName();
	Map<String, String> replicationFactorInReq = databaseConfig
	    .getReplication();
	try
	{
	    String replicaion = formatReplicationFactor(keyspaceName,
	        replicationFactorInReq);

	    String query = "CREATE KEYSPACE " + keyspaceName
	        + " WITH replication "
	        + "= {'class': 'NetworkTopologyStrategy'," + replicaion + "};";
	    myClusterConnection.getSession().execute(query);
	} catch (UnableToProcessException e)
	{
	    throw e;
	} catch (AlreadyExistsException e)
	{
	    throw new AlreadyExistException("Keyspace Already exists", e);
	}
    }

    public synchronized boolean createKeyspaceIfNotExists(
        DbConfigInternal databaseConfig) throws DbServiceException
    {
	String keyspaceName = databaseConfig.getDatabaseName();

	Map<String, String> replicationFactorInReq = databaseConfig
	    .getReplication();
	try
	{
	    String replicaion = formatReplicationFactor(keyspaceName,
	        replicationFactorInReq);

	    String query = "CREATE KEYSPACE IF NOT EXISTS " + keyspaceName
	        + " WITH replication "
	        + "= {'class': 'NetworkTopologyStrategy'," + replicaion + "};";
	    ResultSet resultSet = myClusterConnection.getSession()
	        .execute(query);
	    LOGGER.info(resultSet.toString());
	} catch (UnableToProcessException e)
	{
	    throw e;
	} catch (AlreadyExistsException e)
	{
	    throw new AlreadyExistException("Keyspace Already exists", e);
	}
	return true;
    }

    private String formatReplicationFactor(String keyspaceName,
        Map<String, String> replicationFactorInReq) throws DbServiceException
    {
	List<CharSequence> replicationFactor = new ArrayList<>();

	for (Map.Entry<String, String> replicationEntry : replicationFactorInReq
	    .entrySet())
	{
	    String datacenter = replicationEntry.getKey().toString();
	    try
	    {
		int replication = Integer
		    .parseInt(replicationEntry.getValue().toString());
		replicationFactor.add(String.format(REPLICATION_FORMAT, QUOTE,
		    datacenter, QUOTE, replication));
	    } catch (NumberFormatException e)
	    {
		throw new DbServiceException(String.format(
		    "Invalid replicaiton factor given for database : %s",
		    keyspaceName));
	    }

	}

	String replicaion = "";
	int size = replicationFactor.size();
	int i = 0;
	while (true)
	{
	    replicaion += replicationFactor.get(i);
	    i++;
	    if (i < size)
	    {
		replicaion += ",";
	    } else
	    {
		break;
	    }
	}
	return replicaion;
    }
}
