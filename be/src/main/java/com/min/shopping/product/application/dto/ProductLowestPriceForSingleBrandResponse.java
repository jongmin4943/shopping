package com.min.shopping.product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductLowestPriceForSingleBrandResponse {
    private Long brandId;
    private String brandName;
    private List<ProductResponse> products;

    public BigDecimal getTotalPrice() {
        return products.stream()
                .map(ProductResponse::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void bindProducts(final List<ProductResponse> products) {
        this.products = products;
    }
}
