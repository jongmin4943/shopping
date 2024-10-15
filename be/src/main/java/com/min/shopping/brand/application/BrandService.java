package com.min.shopping.brand.application;

import com.min.shopping.brand.application.dto.BrandCreateRequest;
import com.min.shopping.brand.application.dto.BrandModifyRequest;
import com.min.shopping.brand.application.dto.BrandResponse;

import java.util.List;

public interface BrandService {
    BrandResponse save(BrandCreateRequest request);

    void update(Long id, BrandModifyRequest request);

    void delete(Long id);

    List<BrandResponse> findAll();
}
