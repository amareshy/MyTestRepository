package com.cassandradb.client.client.service.exceptions;

/**
 * Data Access top level exception. All checked exceptions will be child of this
 * exception.
 */
public class DbServiceException extends Exception
{
    private static final long serialVersionUID = 1L;

    public DbServiceException(String message)
    {
	super(message);
    }

    public DbServiceException(Throwable cause)
    {
	super(cause);
    }

    public DbServiceException(String message, Throwable cause)
    {
	super(message, cause);
    }

}
