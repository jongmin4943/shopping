package com.min.shopping.brand.domain;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {
    Brand save(Brand brand);

    void delete(Brand brand);

    List<Brand> findAll();

    Optional<Brand> findById(Long id);
}
