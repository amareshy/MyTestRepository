package com.frs.dataaccess.client.service.status;

/**
 * The {@link StatusAdmin} provides methods to get status of the connection.
 */
public interface StatusAdmin
{
    /**
     * @return true if the CIL client has a connection to the Cassandra cluster, otherwise false.
     */
    boolean isConnected();
}
