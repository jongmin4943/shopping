package com.min.shopping.brand.application.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BrandModifyRequest {

    @NotEmpty(message = "브랜드 이름은 필수값 입니다.")
    private String name;

    public BrandModifyRequest(final String name) {
        this.name = name;
    }
}
