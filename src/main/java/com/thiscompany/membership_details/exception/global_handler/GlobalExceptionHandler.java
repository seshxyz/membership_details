package com.thiscompany.membership_details.exception.global_handler;

import com.thiscompany.membership_details.exception.ExternalApiError;
import com.thiscompany.membership_details.exception.NotFoundException;
import com.thiscompany.membership_details.exception.TokenNotSpecifiedException;
import com.thiscompany.membership_details.exception.UserNotFoundException;
import com.thiscompany.membership_details.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.camel.processor.ThrottlerRejectedExecutionException;
import org.apache.hc.client5.http.HttpHostConnectException;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException ex) {
        return ResponseEntity.of(createProblemDetail(
             messageSource,
             HttpStatus.BAD_REQUEST,
             "invalid.request_body",
            "error.400",
             null
        )).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.of(createProblemDetail(
             messageSource,
             HttpStatus.BAD_REQUEST,
            "invalid.request_body",
            "error.400",
             null
        )).build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.of(createProblemDetail(
             messageSource,
             HttpStatus.UNSUPPORTED_MEDIA_TYPE,
             "unsupported.content_type",
            "error.400",
             null
        )).build();
    }

    @ExceptionHandler(TokenNotSpecifiedException.class)
    public ResponseEntity<ProblemDetail> handleEmptyTokenHeaderException(TokenNotSpecifiedException ex) {
        return ResponseEntity.of(createProblemDetail(
             messageSource,
             HttpStatus.UNAUTHORIZED,
             "empty.token.header",
             "missing.headers",
             new Object[]{Utils.Const.TOKEN_HEADER}
        )).build();
    }

    @ExceptionHandler(ThrottlerRejectedExecutionException.class)
    public ResponseEntity<ProblemDetail> handleThrottleRejectedExecutionException(ThrottlerRejectedExecutionException ex) {
        return ResponseEntity.of(createProblemDetail(
             messageSource,
             HttpStatus.TOO_MANY_REQUESTS,
             "too.many.requests",
             "too.many.requests",
             null
        )).build();
    }

    @ExceptionHandler(ExternalApiError.class)
    public ResponseEntity<ProblemDetail> handleExternalApiError(ExternalApiError ex) {
        ProblemDetail problemDetail = createProblemDetail(
            messageSource,
            HttpStatus.BAD_REQUEST,
            "error.400.external.reason",
            "error.400",
            null
        );
        problemDetail.setProperties(
            Map.of("Error code: ", ex.getErrorCode(),
                "Error message:", ex.getErrorMessage())
        );
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler({UserNotFoundException.class, NotFoundException.class})
    public ResponseEntity<ProblemDetail> handleNotFoundException(UserNotFoundException e, NotFoundException ex) {
        return ResponseEntity.of(createProblemDetail(
            messageSource,
            HttpStatus.NOT_FOUND,
            "error.404",
            "error.404",
            null
        )).build();
    }

    @ExceptionHandler({ResourceAccessException.class, HttpHostConnectException.class})
    public ResponseEntity<ProblemDetail> handleResourceAccessException(Exception e) {
        return ResponseEntity.of(createProblemDetail(
            messageSource,
            HttpStatus.SERVICE_UNAVAILABLE,
            "error.503",
            "error.503",
            null
        )).build();
    }

        public static ProblemDetail createProblemDetail(MessageSource messageSource, HttpStatus status, String messageOrCode, String defaultMessage, Object[] args) {
            String detail = messageSource.getMessage(messageOrCode, args, defaultMessage,
                Locale.ROOT);
            if(detail == null) {
                detail = defaultMessage;
                return ProblemDetail.forStatusAndDetail(status, detail);
            }
            return ProblemDetail.forStatusAndDetail(status, detail);
        }
}
