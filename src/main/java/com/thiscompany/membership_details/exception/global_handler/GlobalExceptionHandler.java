package com.thiscompany.membership_details.exception.global_handler;

import com.thiscompany.membership_details.exception.ExternalApiError;
import com.thiscompany.membership_details.exception.NotFoundException;
import com.thiscompany.membership_details.exception.TokenNotDefinedException;
import com.thiscompany.membership_details.exception.UserNotFoundException;
import com.thiscompany.membership_details.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.camel.processor.ThrottlerRejectedExecutionException;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException ex) {
        ProblemDetail problemDetail = createProblemDetail(
             messageSource,
             HttpStatus.BAD_REQUEST,
             "invalid.request_body",
            "error.400",
             null
        );
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ProblemDetail problemDetail = createProblemDetail(
             messageSource,
             HttpStatus.BAD_REQUEST,
            "invalid.request_body",
            "error.400",
             null
        );
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        ProblemDetail problemDetail = createProblemDetail(
             messageSource,
             HttpStatus.UNSUPPORTED_MEDIA_TYPE,
             "unsupported.content_type",
            "error.400",
             null
        );
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(TokenNotDefinedException.class)
    public ResponseEntity<ProblemDetail> handleEmptyTokenHeaderException(TokenNotDefinedException ex) {
        ProblemDetail problemDetail = createProblemDetail(
             messageSource,
             HttpStatus.UNAUTHORIZED,
             "empty.token.header",
             "missing.headers",
             new Object[]{Utils.Const.TOKEN_HEADER}
        );
        return ResponseEntity.of(problemDetail).build();
    }

    @ExceptionHandler(ThrottlerRejectedExecutionException.class)
    public ResponseEntity<ProblemDetail> handleThrottleRejectedExecutionException(ThrottlerRejectedExecutionException ex) {
        ProblemDetail problemDetail = createProblemDetail(
             messageSource,
             HttpStatus.TOO_MANY_REQUESTS,
             "too.many.requests",
             "too.many.requests",
             null
        );
        return ResponseEntity.of(problemDetail).build();
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
        ProblemDetail problemDetail = createProblemDetail(
            messageSource,
            HttpStatus.NOT_FOUND,
            "error.404",
            "error.404",
            null
        );
        return ResponseEntity.of(problemDetail).build();
    }

        private ProblemDetail createProblemDetail(MessageSource messageSource, HttpStatus status, String messageOrCode, String defaultMessage, Object[] args) {
            String detail = messageSource.getMessage(messageOrCode, args, defaultMessage,
                Locale.ROOT);
            if(detail == null) {
                detail = defaultMessage;
                return ProblemDetail.forStatusAndDetail(status, detail);
            }
            return ProblemDetail.forStatusAndDetail(status, detail);
        }
}
