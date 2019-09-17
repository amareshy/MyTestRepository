package com.frs.services.validator;

public abstract class RequestValidator
{
    public static boolean isNullOrEmpty(String value)
    {
	if (value == null || value.isEmpty())
	{
	    return true;
	}
	return false;
    }
}
