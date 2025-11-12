package com.thiscompany.membership_details.exception_handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorException extends RuntimeException {

    private final HttpStatus status;

    private final Object[] args;

    public ApiErrorException(String message, HttpStatus status, Object[] args) {
        super(message);
        this.status = status;
        this.args = args;
    }
}
