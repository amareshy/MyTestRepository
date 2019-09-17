package com.frs.services.validator;

import com.frs.generated.usermanagement.CreateUserRequest;

public class UserManagementServiceValidator extends RequestValidator
{
    public static void validateUserCreation(CreateUserRequest request)
    {
	if (isNullOrEmpty(request.getEmail())
	    || isNullOrEmpty(request.getFirstName())
	    || isNullOrEmpty(request.getLastName()))
	{

	}
    }
}
