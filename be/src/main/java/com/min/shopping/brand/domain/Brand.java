package com.min.shopping.brand.domain;

import com.min.shopping.brand.exception.BrandCreateException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.util.StringUtils;

@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    protected Brand() {
    }

    public Brand(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new BrandCreateException("브랜드 이름은 필수값 입니다.");
        }

        if (name.length() > 100) {
            throw new BrandCreateException("브랜드 이름은 100자 이하여야 합니다.");
        }
    }
}
