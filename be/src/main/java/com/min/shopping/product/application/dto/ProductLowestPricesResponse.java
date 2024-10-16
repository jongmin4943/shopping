package com.min.shopping.product.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductLowestPricesResponse {
    private List<ProductResponse> products;

    public BigDecimal getTotalPrice() {
        return products.stream()
                .map(ProductResponse::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
