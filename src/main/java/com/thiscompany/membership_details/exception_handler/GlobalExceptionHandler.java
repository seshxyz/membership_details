package com.thiscompany.membership_details.exception_handler;

import com.thiscompany.membership_details.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ProblemDetail handleBindException(BindException ex) {
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageSource.getMessage(
                     "invalid.input.data", null,"bad.request", LocaleContextHolder.getLocale()
                )
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ProblemDetail.forStatusAndDetail(
             HttpStatus.BAD_REQUEST,
             messageSource.getMessage(
                  "missing.request_body", null,"bad.request", LocaleContextHolder.getLocale()
             )
        );
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ProblemDetail handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return ProblemDetail.forStatusAndDetail(
             HttpStatus.BAD_REQUEST,
             messageSource.getMessage(
                  "unsupported.content_type", null,"bad.request", LocaleContextHolder.getLocale()
             )
        );
    }

    @ExceptionHandler(EmptyTokenHeaderException.class)
    public ProblemDetail handleEmptyTokenHeaderException(EmptyTokenHeaderException ex) {
        System.out.println(Arrays.asList(ex.getArgs()));
        return ProblemDetail.forStatusAndDetail(
             HttpStatus.UNAUTHORIZED,
             messageSource.getMessage(
                  "empty.token.header", new Object[]{Utils.Const.TOKEN_HEADER}, "missing.headers", LocaleContextHolder.getLocale()
             )
        );
    }

}
