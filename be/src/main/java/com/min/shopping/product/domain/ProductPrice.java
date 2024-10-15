package com.min.shopping.product.domain;

import com.min.shopping.product.exception.ProductCreateException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    public static final ProductPrice ZERO = new ProductPrice(BigDecimal.ZERO);

    @Column(nullable = false)
    private BigDecimal price;

    public static ProductPrice of(final BigDecimal amount) {
        return new ProductPrice(amount);
    }

    protected ProductPrice() {
    }

    ProductPrice(final BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(final BigDecimal amount) {
        if (amount == null) {
            throw new ProductCreateException("상품의 가격은 필수값 입니다.");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductCreateException("가격은 0보다 작을 수 없습니다.");
        }
    }

    public ProductPrice plus(final ProductPrice amount) {
        return new ProductPrice(this.price.add(amount.price));
    }

    public ProductPrice minus(final ProductPrice amount) {
        return new ProductPrice(this.price.subtract(amount.price));
    }

    public static ProductPrice sum(final Collection<ProductPrice> bags) {
        return bags.stream().reduce(ProductPrice.ZERO, ProductPrice::plus);
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof final ProductPrice that)) return false;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price);
    }
}
