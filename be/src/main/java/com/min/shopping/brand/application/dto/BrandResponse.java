package com.min.shopping.brand.application.dto;

import com.min.shopping.brand.domain.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    private Long id;
    private String name;
    private boolean active;

    public static BrandResponse from(final Brand brand) {
        return new BrandResponse(brand.getId(), brand.getName(), brand.isActive());
    }
}
