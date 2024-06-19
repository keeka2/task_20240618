package com.example.sos.common.error;

import com.example.sos.common.error.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class CommonErrorHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BadRequestErrorResponse> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException exception) {
        log.info("MethodArgumentTypeMismatchException happened.", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BadRequestErrorResponse());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestErrorResponse> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception) {
        log.info("MethodArgumentNotValidException happened.", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BadRequestErrorResponse());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BadRequestErrorResponse> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException exception) {
        log.info("HttpMessageNotReadableException happened.", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BadRequestErrorResponse());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<NotFoundErrorResponse> handleNoHandlerFoundException(
            final NoHandlerFoundException exception) {
        log.info("NoHandlerFoundException happened.", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundErrorResponse());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<MethodNotAllowedErrorResponse> handleHttpRequestMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException exception) {
        log.info("HttpRequestMethodNotSupportedException happened.", exception);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new MethodNotAllowedErrorResponse());
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<NotImplementedErrorResponse> handleNotImplementedException(
            final NotImplementedException exception) {
        log.error("NotImplementedException happened.", exception);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(new NotImplementedErrorResponse());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UnknownErrorResponse> handleAllUncaughtException(
            final Exception exception) {
        log.error("Uncaught error happened.", exception);
        return ResponseEntity.internalServerError().body(new UnknownErrorResponse());
    }
}
