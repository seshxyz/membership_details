package com.thiscompany.membership_details.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiCoreException extends RuntimeException {

    private final HttpStatus status;

    private final Object[] args;

    public ApiCoreException(String message, HttpStatus status, Object[] args) {
        super(message);
        this.status = status;
        this.args = args;
    }
}
