package com.min.shopping.product.application.dto;

import com.min.shopping.common.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductResponse {
    private Long id;
    private Long brandId;
    private Category category;
    private long price;
}
