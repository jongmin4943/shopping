package com.min.shopping.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ShoppingException.class)
    protected ResponseEntity<?> handleShoppingException(final ShoppingException e) {
        logger.error("GlobalExceptionHandler.handleShoppingException", e);
        final ErrorResponse errorResponse = ErrorResponse.of(e.getMessage(), e.getStatus());
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindExceptionException(final BindException e) {
        logger.error("GlobalExceptionHandler.handleMethodArgumentNotValidException", e);
        String errorMessage = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        final ErrorResponse errorResponse = ErrorResponse.of(errorMessage, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleUnexpectedException(final Exception e) {
        logger.error("GlobalExceptionHandler.handleUnexpectedException", e);
        final ErrorResponse errorResponse = ErrorResponse.of(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
