package com.cassandradb.client.client;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.cassandradb.client.client.datasource.ClusterConnection;
import com.cassandradb.client.client.service.AsyncConnectionRequestHandler;
import com.cassandradb.client.client.service.ConnectionRequestHandler;
import com.cassandradb.client.client.service.DBCluster;
import com.cassandradb.client.client.service.configuration.ConfigurationAdmin;
import com.cassandradb.client.client.service.exceptions.DbClusterInitializationException;
import com.cassandradb.client.client.service.iterator.EntityIteratorFactory;
import com.cassandradb.client.client.service.status.StatusAdmin;

@Component("dbCluster")
public class DBClusterImpl implements DBCluster
{
    private static final Logger LOG = LoggerFactory
        .getLogger(DBClusterImpl.class);

    @Autowired(required = true)
    private ClusterConnection myClusterConnection;

    @Autowired(required = true)
    @Qualifier("myConnectionRequestHandler")
    private ConnectionRequestHandler myConnectionRequestHandler;

    @Autowired(required = true)
    @Qualifier("myAsyncConnectionRequestHandler")
    private AsyncConnectionRequestHandler myAsyncConnectionRequestHandler;

    @Autowired(required = true)
    private ConfigurationAdmin myConfigurationAdmin;

    @Autowired
    private StatusAdmin myStatusAdmin;

    public DBClusterImpl()
    {

    }

    private void setupConnection()
    {
	myClusterConnection.connectCluster();
    }

    @PostConstruct
    public void init() throws DbClusterInitializationException
    {
	if (myClusterConnection == null)
	{
	    LOG.error(
	        "ClusterConnection is not initialized and injected to DbCluster.");
	    throw new DbClusterInitializationException(
	        "ClusterConnection is not initialized and injected to DbCluster.");
	}

	if (!myClusterConnection.isConnected())
	{
	    setupConnection();
	}

    }

    @Override
    public ConnectionRequestHandler getConnectionRequestHandler()
    {
	return myConnectionRequestHandler;
    }

    @Override
    public AsyncConnectionRequestHandler getAsyncConnectionRequestHandler()
    {
	return myAsyncConnectionRequestHandler;
    }

    @Override
    public EntityIteratorFactory getEntityIteratorFactory()
    {
	return null;
    }

    @Override
    public StatusAdmin getStatusAdmin()
    {
	return myStatusAdmin;
    }

    @Override
    public ConfigurationAdmin getConfigurationAdmin()
    {
	return myConfigurationAdmin;
    }

}
