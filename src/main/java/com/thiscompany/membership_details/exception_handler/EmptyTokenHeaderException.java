package com.thiscompany.membership_details.exception_handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmptyTokenHeaderException extends ApiErrorException {

    public EmptyTokenHeaderException(Object[] args) {
        super("empty.token.header", HttpStatus.UNAUTHORIZED, args);
    }
}
