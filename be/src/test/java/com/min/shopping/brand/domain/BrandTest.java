package com.min.shopping.brand.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class BrandTest {

    @Test
    @DisplayName("브랜드를 생성할 수 있다.")
    void validBrand() {
        final Brand brand = new Brand("test");

        assertSoftly(softly -> {
            softly.assertThat(brand.getName()).isEqualTo("test");
            softly.assertThat(brand.isActive()).isFalse();
        });
    }

    @Test
    @DisplayName("브랜드의 이름을 수정할 수 있다.")
    void modifyBrandName() {
        final Brand brand = new Brand("test");
        brand.modifyName("modified");

        assertThat(brand.getName()).isEqualTo("modified");
    }

    @Test
    @DisplayName("브랜드의 활성화 상태를 수정할 수 있다.")
    void modifyBrandStatus() {
        final Brand brand = new Brand("test");

        brand.activate();

        assertThat(brand.isActive()).isTrue();
    }

}
