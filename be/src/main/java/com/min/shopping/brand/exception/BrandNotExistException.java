package com.min.shopping.brand.exception;

import com.min.shopping.common.exception.NotFoundException;

public class BrandNotExistException extends NotFoundException {
    public BrandNotExistException(final String message) {
        super(message);
    }
}
