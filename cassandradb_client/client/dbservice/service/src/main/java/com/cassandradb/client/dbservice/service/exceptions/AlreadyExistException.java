package com.cassandradb.client.dbservice.service.exceptions;

public class AlreadyExistException extends DbServiceException {
    private static final long serialVersionUID = 1L;

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistException(Throwable cause) {
        super(cause);
    }


}
