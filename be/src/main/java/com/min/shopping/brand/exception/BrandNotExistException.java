package com.min.shopping.brand.exception;

public class BrandNotExistException extends RuntimeException {
    public BrandNotExistException(final String message) {
        super(message);
    }
}
