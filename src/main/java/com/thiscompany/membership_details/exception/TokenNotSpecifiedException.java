package com.thiscompany.membership_details.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TokenNotSpecifiedException extends ApiCoreException {

    public TokenNotSpecifiedException() {
        super("empty.token.header", HttpStatus.UNAUTHORIZED, new Object[]{});
    }
}
