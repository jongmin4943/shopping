package com.min.shopping.product.domain;

import com.min.shopping.common.Category;
import com.min.shopping.product.exception.ProductCreateException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(indexes = {
        @Index(name = "idx_product_brand_category", columnList = "brandId, category"),
        @Index(name = "idx_product_category_price", columnList = "category, price")
})
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

    @Embedded
    private ProductPrice price;

    public Product(final Long brandId, final Category category, final BigDecimal price) {
        validate(brandId, category);
        this.brandId = brandId;
        this.category = category;
        this.price = new ProductPrice(price);
    }

    private void validate(final Long brandId, final Category category) {
        if (brandId == null) {
            throw new ProductCreateException("상품의 브랜드는 필수값 입니다.");
        }

        validateCategory(category);
    }

    public void modify(final Category category, final BigDecimal price) {
        validateCategory(category);
        this.category = category;
        this.price = new ProductPrice(price);
    }

    private void validateCategory(final Category category) {
        if (category == null) {
            throw new ProductCreateException("상품의 카테고리는 필수값 입니다.");
        }
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }
}
