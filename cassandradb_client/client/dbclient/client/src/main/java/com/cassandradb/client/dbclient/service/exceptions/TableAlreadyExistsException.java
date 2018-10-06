package com.cassandradb.client.dbclient.service.exceptions;

/**
 * Throws when Table already exists and trying to create a new with same name.
 *
 * @author Amaresh Yadav
 */
public class TableAlreadyExistsException extends AlreadyExistException {
    private static final long serialVersionUID = 1L;

    public TableAlreadyExistsException(String message) {
        super(message);
    }

    public TableAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
