package com.min.shopping.brand.domain;

import java.util.List;

public interface BrandRepository {
    Brand save(Brand brand);

    void delete(Brand brand);

    List<Brand> findAll();
}
