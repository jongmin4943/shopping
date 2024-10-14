package com.min.shopping.brand.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BrandTest {

    @Test
    @DisplayName("브랜드를 생성할 수 있다.")
    void validBrand() {
        Assertions.assertDoesNotThrow(() -> new Brand("test"));
    }

}
