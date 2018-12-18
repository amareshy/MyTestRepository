package com.cassandradb.client.client.service.exceptions;

public class DbClusterInitializationException extends DbServiceException {

    private static final long serialVersionUID = 1L;

    public DbClusterInitializationException(String message) {
        super(message);
    }

    public DbClusterInitializationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DbClusterInitializationException(Throwable throwable) {
        super(throwable);
    }

}
