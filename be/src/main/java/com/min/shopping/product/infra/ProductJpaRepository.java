package com.min.shopping.product.infra;

import com.min.shopping.product.domain.Product;
import com.min.shopping.product.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends ProductRepository, JpaRepository<Product, Long> {

}
