package com.thiscompany.membership_details.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends ApiCoreException {

	public NotFoundException(String message, Object[] args) {
		super(message, HttpStatus.NOT_FOUND, args);
	}

}
