package com.min.shopping.product.exception;

import com.min.shopping.common.exception.BadRequestException;

public class ProductCreateException extends BadRequestException {
    public ProductCreateException(final String message) {
        super(message);
    }
}
