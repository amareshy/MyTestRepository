package com.cassandradb.client.dbservice.service.exceptions;

/**
 * Thrown when database exists and trying to create new one with the same name.
 */
public class DatabaseAlreadyExistsException extends AlreadyExistException {
    private static final long serialVersionUID = 1L;

    public DatabaseAlreadyExistsException(String message) {
        super(message);
    }


}
