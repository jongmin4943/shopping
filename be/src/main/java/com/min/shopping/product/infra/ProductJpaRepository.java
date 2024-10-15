package com.min.shopping.product.infra;

import com.min.shopping.product.domain.Product;
import com.min.shopping.product.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductJpaRepository extends ProductRepository, JpaRepository<Product, Long> {

    @Override
    @Query("SELECT COUNT(DISTINCT p.category) FROM Product p WHERE p.brandId = :brandId")
    int countProductCategoryTypesByBrand(@Param("brandId") Long brandId);

}
