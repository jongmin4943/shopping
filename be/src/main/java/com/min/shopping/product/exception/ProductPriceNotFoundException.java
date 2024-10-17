package com.min.shopping.product.exception;

import com.min.shopping.common.exception.NotFoundException;

public class ProductPriceNotFoundException extends NotFoundException {
    public ProductPriceNotFoundException(final String message) {
        super(message);
    }
}
