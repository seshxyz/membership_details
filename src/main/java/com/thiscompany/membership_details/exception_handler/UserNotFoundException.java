package com.thiscompany.membership_details.exception_handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends ApiErrorException{

    public UserNotFoundException() {
        super("user.not_found", HttpStatus.NOT_FOUND, null);
    }

}
