package com.cassandradb.client.dbclient.service.status;

/**
 * The {@link StatusAdmin} provides methods to get status of the connection.
 */
public interface StatusAdmin {

    boolean isConnected();
}
