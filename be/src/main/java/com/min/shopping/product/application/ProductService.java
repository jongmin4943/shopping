package com.min.shopping.product.application;

import com.min.shopping.product.application.dto.ProductCreateRequest;
import com.min.shopping.product.application.dto.ProductModifyRequest;
import com.min.shopping.product.application.dto.ProductResponse;
import com.min.shopping.product.domain.Product;
import com.min.shopping.product.domain.ProductRepository;
import com.min.shopping.product.exception.ProductNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse save(final ProductCreateRequest request) {
        final Product product = new Product(request.getBrandId(), request.getCategory(), request.getPrice());

        final Product saved = productRepository.save(product);

        return ProductResponse.from(saved);
    }

    public ProductResponse findById(final Long id) {
        final Product product = findProductById(id);
        return ProductResponse.from(product);
    }

    public void update(final Long id, final ProductModifyRequest request) {
        final Product product = findProductById(id);

        product.modify(request.getCategory(), request.getPrice());

        productRepository.save(product);
    }

    private Product findProductById(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException("상품이 존재하지 않습니다."));
    }

    public void delete(final Long id) {

    }
}
