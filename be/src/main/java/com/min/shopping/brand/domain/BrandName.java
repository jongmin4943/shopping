package com.min.shopping.brand.domain;

import com.min.shopping.brand.exception.BrandCreateException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Embeddable
public class BrandName {
    public static final int MAX_LENGTH = 100;

    @Getter
    @Column(name = "name", length = MAX_LENGTH)
    private String name;

    protected BrandName() {
    }

    public BrandName(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new BrandCreateException("브랜드 이름은 필수값 입니다.");
        }

        if (name.length() > MAX_LENGTH) {
            throw new BrandCreateException("브랜드 이름은 100자 이하여야 합니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof BrandName)) return false;
        final BrandName brandName = (BrandName) o;
        return Objects.equals(name, brandName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
