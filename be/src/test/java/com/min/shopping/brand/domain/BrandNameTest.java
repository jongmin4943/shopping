package com.min.shopping.brand.domain;

import com.min.shopping.brand.exception.BrandCreateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BrandNameTest {
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("브랜드 이름이 null 이거나 공백일 경우 예외가 발생한다.")
    void validateBrandNameNotNullOrEmpty(final String name) {
        assertThatThrownBy(() -> new BrandName(name))
                .isInstanceOf(BrandCreateException.class)
                .hasMessage("브랜드 이름은 필수값 입니다.");
    }

    @Test
    @DisplayName("브랜드 이름이 100자를 초과할 경우 예외가 발생한다.")
    void validateBrandNameLength() {
        assertThatThrownBy(() -> new BrandName("a".repeat(101)))
                .isInstanceOf(BrandCreateException.class)
                .hasMessage("브랜드 이름은 100자 이하여야 합니다.");
    }
}
