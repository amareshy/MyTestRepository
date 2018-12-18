package com.cassandradb.client.client.persistence.requesthandler;

import org.springframework.beans.factory.annotation.Autowired;

import com.cassandradb.client.client.configuraion.DatabaseConfig;
import com.cassandradb.client.client.datasource.ClusterConnection;
import com.cassandradb.client.client.service.exceptions.UnableToProcessException;
import com.datastax.driver.core.ResultSet;

/**
 * This Class is responsible to create and modify databases and tables in
 * Casandra.
 */
public class DatabaseIfHandler
{
    @Autowired
    private ClusterConnection myClusterConnection;

    public DatabaseIfHandler()
    {
    }

    public synchronized void createKeyspace(DatabaseConfig databaseConfig)
        throws UnableToProcessException
    {
	try
	{
	    String query = "CREATE KEYSPACE " + databaseConfig.getDatabaseName()
	        + " WITH replication "
	        + "= {'class':'SimpleStrategy', 'replication_factor':1};";
	    ResultSet resultSet = myClusterConnection.getSession()
	        .execute(query);
	    System.out.println(resultSet.all());
	} catch (UnableToProcessException e)
	{
	    throw e;
	}

    }

}
