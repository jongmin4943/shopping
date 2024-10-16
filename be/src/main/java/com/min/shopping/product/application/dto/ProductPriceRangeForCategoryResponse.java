package com.min.shopping.product.application.dto;

import com.min.shopping.common.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductPriceRangeForCategoryResponse {
    private Category category;
    private BrandProduct lowestPriceProduct;
    private BrandProduct highestPriceProduct;

    public static BrandProduct createBrandProduct(final String brandName, final Long productId, final BigDecimal price) {
        return new BrandProduct(brandName, productId, price);
    }

    @Getter
    @AllArgsConstructor
    public static class BrandProduct {
        private String brandName;
        private Long productId;
        private BigDecimal price;
    }
}
