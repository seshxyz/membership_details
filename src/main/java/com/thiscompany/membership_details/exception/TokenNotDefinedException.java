package com.thiscompany.membership_details.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TokenNotDefinedException extends ApiCoreException {

    public TokenNotDefinedException(Object[] args) {
        super("empty.token.header", HttpStatus.UNAUTHORIZED, args);
    }
}
