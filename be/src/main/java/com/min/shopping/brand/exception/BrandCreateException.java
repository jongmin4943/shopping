package com.min.shopping.brand.exception;

import com.min.shopping.common.exception.BadRequestException;

public class BrandCreateException extends BadRequestException {
    public BrandCreateException(final String message) {
        super(message);
    }
}
