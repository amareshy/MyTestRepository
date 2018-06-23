// **********************************************************************
// Copyright (c) 2015 Telefonaktiebolaget LM Ericsson, Sweden.
// All rights reserved.
// The Copyright to the computer program(s) herein is the property of
// Telefonaktiebolaget LM Ericsson, Sweden.
// The program(s) may be used and/or copied with the written permission
// from Telefonaktiebolaget LM Ericsson or in accordance with the terms
// and conditions stipulated in the agreement/contract under which the
// program(s) have been supplied.
// **********************************************************************
package com.cassandradb.client.dbservice.exceptions;

/**
 * This exception will be thrown when no configuration is found.
 * @author Amaresh Yadav
 */
public class ConfigNotFoundException extends DbServiceException
{
    private static final long serialVersionUID = 8835599929721992634L;

    /**
     * Constructs a ConfigNotFoundException with the specified message
     *
     * @param message the message for the exception
     */
    public ConfigNotFoundException(final String message)
    {
        super(message);
    }

    /**
     * Constructs a ConfigNotFoundException with the specified cause
     *
     * @param cause the cause of the exception
     */
    public ConfigNotFoundException(final Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a ConfigNotFoundException with the specified message and cause
     * @param message the message for the exception
     * @param cause the cause of the exception
     */
    public ConfigNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
