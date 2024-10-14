package com.min.shopping.brand.infra;

import com.min.shopping.brand.domain.Brand;
import com.min.shopping.brand.domain.BrandRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandJpaRepository extends BrandRepository, JpaRepository<Brand, Long> {

}
