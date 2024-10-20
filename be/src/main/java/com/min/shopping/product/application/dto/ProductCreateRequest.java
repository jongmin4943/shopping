package com.min.shopping.product.application.dto;

import com.min.shopping.common.Category;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductCreateRequest {

    @NotNull(message = "브랜드는 필수값 입니다.")
    private Long brandId;

    @NotNull(message = "카테고리는 필수값 입니다.")
    private Category category;

    @NotNull(message = "가격은 필수값 입니다.")
    private BigDecimal price;

}
