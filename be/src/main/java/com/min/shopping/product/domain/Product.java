package com.min.shopping.product.domain;

import com.min.shopping.common.Category;
import com.min.shopping.product.exception.ProductCreateException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Getter
    private Long brandId;

    @Enumerated(EnumType.STRING)
    @Getter
    private Category category;

    @Column(nullable = false)
    @Getter
    private BigDecimal price;

    public Product(final Long brandId, final Category category, final BigDecimal price) {
        validate(brandId, category, price);
        this.brandId = brandId;
        this.category = category;
        this.price = price;
    }

    private void validate(final Long brandId, final Category category, final BigDecimal price) {
        if (brandId == null) {
            throw new ProductCreateException("상품의 브랜드는 필수값 입니다.");
        }

        if (category == null) {
            throw new ProductCreateException("상품의 카테고리는 필수값 입니다.");
        }

        if (price == null) {
            throw new ProductCreateException("상품의 가격은 필수값 입니다.");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductCreateException("가격은 0보다 작을 수 없습니다.");
        }

    }


}
