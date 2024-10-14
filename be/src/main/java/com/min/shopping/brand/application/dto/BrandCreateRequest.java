package com.min.shopping.brand.application.dto;

import com.min.shopping.common.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandCreateRequest {

    @NotEmpty(message = "브랜드 이름은 필수값 입니다.")
    private String name;

    @NotNull(message = "카테고리별 상품 가격은 필수값 입니다.")
    private Map<Category, Long> productPrices;

    public BrandCreateRequest(final String name) {
        this.name = name;
    }
}
