package com.min.shopping.product.exception;

import com.min.shopping.common.exception.NotFoundException;

public class ProductNotExistException extends NotFoundException {
    public ProductNotExistException(final String message) {
        super(message);
    }
}
