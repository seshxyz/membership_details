package com.thiscompany.membership_details.exception;

import lombok.Getter;

@Getter
public class ExternalApiError extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;

    public ExternalApiError(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
