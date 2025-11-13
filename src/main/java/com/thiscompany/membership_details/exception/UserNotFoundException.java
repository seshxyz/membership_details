package com.thiscompany.membership_details.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String id) {
        super("user.not_found", new Object[]{id});
    }

}
