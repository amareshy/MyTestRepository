package com.frs.dataaccess.client.service.exceptions;

public class InvalidRequestException extends DbServiceException {
	
	private static final long serialVersionUID = 1L;
	
	public InvalidRequestException(String message) {
		super(message);
	}

	public InvalidRequestException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public InvalidRequestException(Throwable throwable) {
		super(throwable);
	}
}
