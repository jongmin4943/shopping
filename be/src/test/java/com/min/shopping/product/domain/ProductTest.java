package com.min.shopping.product.domain;

import com.min.shopping.common.Category;
import com.min.shopping.product.exception.ProductCreateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    @Test
    @DisplayName("브랜드 ID가 null 일 경우 예외가 발생한다.")
    void validateBrandIdNotNull() {
        assertThatThrownBy(() -> new Product(null, Category.HAT, BigDecimal.valueOf(1000)))
                .isInstanceOf(ProductCreateException.class)
                .hasMessage("상품의 브랜드는 필수값 입니다.");
    }

    @Test
    @DisplayName("카테고리가 null 일 경우 예외가 발생한다.")
    void validateCategoryNotNull() {
        assertThatThrownBy(() -> new Product(1L, null, BigDecimal.valueOf(1000)))
                .isInstanceOf(ProductCreateException.class)
                .hasMessage("상품의 카테고리는 필수값 입니다.");
    }

    @Test
    @DisplayName("상품 가격이 null 일 경우 예외가 발생한다.")
    void validatePriceNotNull() {
        assertThatThrownBy(() -> new Product(1L, Category.HAT, null))
                .isInstanceOf(ProductCreateException.class)
                .hasMessage("상품의 가격은 필수값 입니다.");
    }

    @Test
    @DisplayName("상품 가격이 0보다 작을 경우 예외가 발생한다.")
    void validatePriceNotNegative() {
        assertThatThrownBy(() -> new Product(1L, Category.HAT, BigDecimal.valueOf(-100)))
                .isInstanceOf(ProductCreateException.class)
                .hasMessage("가격은 0보다 작을 수 없습니다.");
    }

    @Test
    @DisplayName("상품을 생성할 수 있다.")
    void validProduct() {
        assertDoesNotThrow(() -> new Product(1L, Category.HAT, BigDecimal.valueOf(1000)));
    }
}
