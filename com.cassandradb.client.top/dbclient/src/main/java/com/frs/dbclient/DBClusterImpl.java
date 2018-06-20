package com.frs.dbclient;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.frs.dbclient.persistence.cql.ClusterConnection;
import com.frs.dbclient.persistence.cql.ConnectionImpl;
import com.frs.dbservice.AsyncConnection;
import com.frs.dbservice.Connection;
import com.frs.dbservice.DBCluster;
import com.frs.dbservice.configuration.ConfigurationAdmin;
import com.frs.dbservice.iterator.EntityIteratorFactory;
import com.frs.dbservice.status.StatusAdmin;

@Component("dbCluster")
public class DBClusterImpl implements DBCluster {
	private static final Logger LOG = LoggerFactory.getLogger(DBClusterImpl.class);

	/**
	 * Default value for Cassandra port.
	 */
	public static final int DEFAULT_SERVER_PORT = 12742;
	/**
	 * Default value for Cassandra hostname.
	 *
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

	

	private  ClusterConnection myClusterConnection;
	private  ConnectionImpl myConnection;

	public DBClusterImpl() {
		this(new ClusterConnection());
	}

	DBClusterImpl(ClusterConnection clusterConnection) {
		myClusterConnection = clusterConnection;
		myConnection = new ConnectionImpl();

		if (!myClusterConnection.isConnected()) {
			setupConnection();
		}
	}

	private void setupConnection() {
		myClusterConnection.connectCluster();
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
