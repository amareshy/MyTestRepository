package com.cassandradb.client.dbclient.service.exceptions;

/**
 * Thrown when the cluster is not available to process the request.
 * <p>
 * This exception is thrown when the cluster is considered to be disconnected or not fully available. When this
 * exception is thrown, the request has not been executed because it was aborted before it was sent to replicas.
 * The request may be successful if retried.
 */
public class UnableToProcessException extends ProcessingException {
    private static final long serialVersionUID = 7963976772899280381L;

    public UnableToProcessException(final String message) {
        super(message);
    }

    public UnableToProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnableToProcessException(final Throwable cause) {
        super(cause);
    }
}
