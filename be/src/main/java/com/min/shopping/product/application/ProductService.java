package com.min.shopping.product.application;

import com.min.shopping.product.application.dto.ProductCreateRequest;
import com.min.shopping.product.application.dto.ProductModifyRequest;
import com.min.shopping.product.application.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public ProductResponse save(final @Valid ProductCreateRequest request) {
        return null;
    }

    public List<ProductResponse> findById(final Long id) {
        return null;
    }

    public void update(final Long id, final @Valid ProductModifyRequest request) {

    }

    public void delete(final Long id) {

    }
}
