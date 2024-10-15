package com.min.shopping.product.domain;

import com.min.shopping.common.Category;
import com.min.shopping.product.exception.ProductCreateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
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
    @DisplayName("상품을 생성할 수 있다.")
    void validProduct() {
        assertDoesNotThrow(() -> new Product(1L, Category.HAT, BigDecimal.valueOf(1000)));
    }

    @Test
    @DisplayName("상품의 카테고리와 가격을 수정할 수 있다.")
    void modifyProduct() {
        final Product product = new Product(1L, Category.HAT, BigDecimal.valueOf(1000));
        product.modify(Category.PANTS, BigDecimal.valueOf(2000));

        assertSoftly(softly -> {
            softly.assertThat(product.getCategory()).isEqualTo(Category.PANTS);
            softly.assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(2000));
        });
    }

    @Test
    @DisplayName("카테고리가 null 이면 상품 수정 시 예외가 발생한다.")
    void modifyProductWithNullCategory() {
        final Product product = new Product(1L, Category.HAT, BigDecimal.valueOf(1000));

        assertThatThrownBy(() -> product.modify(null, BigDecimal.valueOf(2000)))
                .isInstanceOf(ProductCreateException.class)
                .hasMessage("상품의 카테고리는 필수값 입니다.");
    }
}
