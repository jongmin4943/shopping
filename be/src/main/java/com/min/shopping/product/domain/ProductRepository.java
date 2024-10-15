package com.min.shopping.product.domain;


import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);

    void delete(Product product);

    Optional<Product> findById(Long id);
}
