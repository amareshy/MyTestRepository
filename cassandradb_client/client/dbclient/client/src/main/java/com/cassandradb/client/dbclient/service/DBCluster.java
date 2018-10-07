package com.cassandradb.client.dbclient.service;

import com.cassandradb.client.dbclient.service.configuration.ConfigurationAdmin;
import com.cassandradb.client.dbclient.service.iterator.EntityIteratorFactory;
import com.cassandradb.client.dbclient.service.status.StatusAdmin;

/**
 * Instance of this class provides handles for Database cluster.
 * <p>
 * All handles returned from this class instance are thread safe and can be reused for many requests in the system.
 * </p>
 *
 * @author Amaresh Yadav
 */
public interface DBCluster {
    /**
     * Returns a {@link ConnectionRequestHandler} to be used for synchronous operations against the cluster.
     *
     * @return the connection
     */
    ConnectionRequestHandler getConnectionRequestHandler();

    /**
     * Returns a {@link AsyncConnectionRequestHandler} to be used for asynchronous operations against the cluster.
     *
     * @return the async connection
     */
    AsyncConnectionRequestHandler getAsyncConnectionRequestHandler();

    /**
     * Returns a {@link EntityIteratorFactory} which provides iterators over Entity Base Keys.
     *
     * @return the entity iterator factory
     */
    EntityIteratorFactory getEntityIteratorFactory();

    /**
     * Returns a {@link StatusAdmin} which provides status of the cluster.
     *
     * @return the status admin
     */
    StatusAdmin getStatusAdmin();

    /**
     * Returns a {@link ConfigurationAdmin} which is used to handle the storage configuration.
     *
     * @return the configuration admin
     */
    ConfigurationAdmin getConfigurationAdmin();


}
