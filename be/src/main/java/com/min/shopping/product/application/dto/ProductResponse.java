package com.min.shopping.product.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.min.shopping.common.Category;
import com.min.shopping.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private Long id;
    private Long brandId;
    private Category category;
    private BigDecimal price;

    public static ProductResponse from(final Product product) {
        return new ProductResponse(product.getId(), product.getBrandId(), product.getCategory(), product.getPrice());
    }
}
