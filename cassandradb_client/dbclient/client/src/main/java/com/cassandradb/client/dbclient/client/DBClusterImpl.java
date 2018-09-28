package com.cassandradb.client.dbclient.client;



import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.cassandradb.client.dbclient.client.persistence.cql.ClusterConnection;
import com.cassandradb.client.dbclient.client.persistence.requesthandler.ConnectionImpl;
import com.cassandradb.client.dbservice.service.AsyncConnection;
import com.cassandradb.client.dbservice.service.Connection;
import com.cassandradb.client.dbservice.service.DBCluster;
import com.cassandradb.client.dbservice.service.configuration.ConfigurationAdmin;
import com.cassandradb.client.dbservice.service.exceptions.DbClusterInitializationException;
import com.cassandradb.client.dbservice.service.iterator.EntityIteratorFactory;
import com.cassandradb.client.dbservice.service.service.status.StatusAdmin;

@Repository("dbCluster")
public class DBClusterImpl implements DBCluster {
    private static final Logger LOG = LoggerFactory.getLogger(DBClusterImpl.class);

    /**
     * Default value for Cassandra port.
     */
    public static final int DEFAULT_SERVER_PORT = 12742;
    /**
     * Default value for Cassandra hostname.
     * <p>
     * This default value is typically only usable in a test environment.
     */
    public static final String DEFAULT_SERVER_HOST = "localhost";
    /**
     * Default value for Cassandra client username
     */
    public static final String DEFAULT_SERVER_USERNAME = "cassandra";
    /**
     * Default value for Cassandra client password
     */
    public static final String DEFAULT_SERVER_PASSWORD = "cassandra";

    @Autowired(required = true)
    private ClusterConnection myClusterConnection;

    @Autowired(required = true)
    @Qualifier("myConnection")
    private ConnectionImpl myConnection;

    public DBClusterImpl() {

    }

    private void setupConnection() {
        myClusterConnection.connectCluster();
    }

    @PostConstruct
    public void init() throws DbClusterInitializationException {
        if (myClusterConnection == null) {
            LOG.error("ClusterConnection is not initialized and injected to DbCluster.");
            throw new DbClusterInitializationException("ClusterConnection is not initialized and injected to DbCluster.");
        }

        if (!myClusterConnection.isConnected()) {
            setupConnection();
        }
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
