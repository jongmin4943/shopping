package com.min.shopping.brand.application;

import com.min.shopping.brand.domain.Brand;

public interface BrandProvider {
    Brand findById(final Long id);
}
