package com.cassandradb.client.client.service.exceptions;

public class ProcessingException extends DbServiceException {

    private static final long serialVersionUID = 1L;

    public ProcessingException(String message) {
        super(message);
    }

    public ProcessingException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ProcessingException(Throwable throwable) {
        super(throwable);
    }
}
