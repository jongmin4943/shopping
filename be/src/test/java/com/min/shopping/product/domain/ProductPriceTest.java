package com.min.shopping.product.domain;

import com.min.shopping.product.exception.ProductCreateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @Test
    @DisplayName("정상적인 금액으로 생성할 수 있다.")
    void createProductPrice() {
        final ProductPrice price = ProductPrice.of(BigDecimal.valueOf(1000));

        assertThat(price.getPrice()).isEqualTo(BigDecimal.valueOf(1000));
    }

    @Test
    @DisplayName("금액이 null 이면 예외가 발생한다.")
    void createProductPriceWithNull() {
        assertThatThrownBy(() -> ProductPrice.of(null))
                .isInstanceOf(ProductCreateException.class)
                .hasMessage("상품의 가격은 필수값 입니다.");
    }

    @Test
    @DisplayName("금액이 0보다 작으면 예외가 발생한다.")
    void createProductPriceWithNegativeAmount() {
        assertThatThrownBy(() -> ProductPrice.of(BigDecimal.valueOf(-1000)))
                .isInstanceOf(ProductCreateException.class)
                .hasMessage("가격은 0보다 작을 수 없습니다.");
    }

    @Test
    @DisplayName("금액을 더할 수 있다.")
    void plusProductPrice() {
        final ProductPrice price1 = ProductPrice.of(BigDecimal.valueOf(1000));
        final ProductPrice price2 = ProductPrice.of(BigDecimal.valueOf(500));

        final ProductPrice result = price1.plus(price2);

        assertThat(result).isEqualTo(ProductPrice.of(BigDecimal.valueOf(1500)));
    }

    @Test
    @DisplayName("금액을 뺄 수 있다.")
    void minusProductPrice() {
        final ProductPrice price1 = ProductPrice.of(BigDecimal.valueOf(1000));
        final ProductPrice price2 = ProductPrice.of(BigDecimal.valueOf(500));

        final ProductPrice result = price1.minus(price2);

        assertThat(result).isEqualTo(ProductPrice.of(BigDecimal.valueOf(500)));
    }

    @Test
    @DisplayName("여러 금액의 합을 구할 수 있다.")
    void sumProductPrices() {
        final List<ProductPrice> prices = List.of(
                ProductPrice.of(BigDecimal.valueOf(1000)),
                ProductPrice.of(BigDecimal.valueOf(500)),
                ProductPrice.of(BigDecimal.valueOf(1500))
        );

        final ProductPrice result = ProductPrice.sum(prices);

        assertThat(result).isEqualTo(ProductPrice.of(BigDecimal.valueOf(3000)));
    }

    @Test
    @DisplayName("동일한 금액일 경우 동등한 객체이다.")
    void equalsProductPrice() {
        final ProductPrice price1 = ProductPrice.of(BigDecimal.valueOf(1000));
        final ProductPrice price2 = ProductPrice.of(BigDecimal.valueOf(1000));

        assertThat(price1).isEqualTo(price2);
    }

}
