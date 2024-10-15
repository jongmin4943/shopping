package com.min.shopping.brand.application;

import com.min.shopping.brand.application.dto.BrandCreateRequest;
import com.min.shopping.brand.application.dto.BrandModifyRequest;
import com.min.shopping.brand.application.dto.BrandResponse;
import com.min.shopping.brand.domain.Brand;
import com.min.shopping.brand.domain.BrandRepository;
import com.min.shopping.brand.exception.BrandCreateException;
import com.min.shopping.brand.exception.BrandNotExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @InjectMocks
    private BrandServiceImpl brandService;

    @Mock
    private BrandRepository brandRepository;

    @Test
    @DisplayName("브랜드를 등록할 수 있다")
    void save() {
        final BrandCreateRequest request = new BrandCreateRequest("종민");
        final Brand brand = new Brand("종민");

        when(brandRepository.existsByName(brand.getName())).thenReturn(false);
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        final BrandResponse response = brandService.save(request);

        assertThat(response.getName()).isEqualTo("종민");
    }

    @Test
    @DisplayName("브랜드 이름이 중복되면 등록 시 예외가 발생한다")
    void saveWhenNameDuplicate() {
        final BrandCreateRequest request = new BrandCreateRequest("종민");
        final Brand brand = new Brand("종민");

        when(brandRepository.existsByName(brand.getName())).thenReturn(true);

        assertThatThrownBy(() -> brandService.save(request))
                .isInstanceOf(BrandCreateException.class)
                .hasMessage("이미 존재하는 브랜드 이름입니다.");
    }

    @Test
    @DisplayName("브랜드를 수정할 수 있다")
    void update() {
        final Long brandId = 1L;
        final BrandModifyRequest request = new BrandModifyRequest("와잼");
        final Brand brand = new Brand("종민");

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(brandRepository.existsByName("와잼")).thenReturn(false);

        brandService.update(brandId, request);

        assertThat(brand.getName()).isEqualTo("와잼");
    }

    @Test
    @DisplayName("브랜드가 존재하지 않으면 수정 시 예외가 발생한다")
    void updateWhenBrandNotExist() {
        final Long brandId = 1L;
        final BrandModifyRequest request = new BrandModifyRequest("와잼");

        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> brandService.update(brandId, request))
                .isInstanceOf(BrandNotExistException.class)
                .hasMessage("브랜드가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("브랜드 이름이 중복되면 수정 시 예외가 발생한다")
    void updateWhenNameDuplicate() {
        final Long brandId = 1L;
        final BrandModifyRequest request = new BrandModifyRequest("와잼");
        final Brand brand = new Brand("종민");

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));
        when(brandRepository.existsByName("와잼")).thenReturn(true);

        assertThatThrownBy(() -> brandService.update(brandId, request))
                .isInstanceOf(BrandCreateException.class)
                .hasMessage("이미 존재하는 브랜드 이름입니다.");
    }

    @Test
    @DisplayName("브랜드를 삭제할 수 있다")
    void delete() {
        final Long brandId = 1L;
        final Brand brand = new Brand("종민");

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brand));

        brandService.delete(brandId);

        verify(brandRepository, times(1)).delete(brand);
    }

    @Test
    @DisplayName("존재하지 않는 브랜드를 삭제하려 할 경우 예외가 발생한다")
    void deleteWhenBrandNotExist() {
        final Long brandId = 1L;

        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> brandService.delete(brandId))
                .isInstanceOf(BrandNotExistException.class)
                .hasMessage("브랜드가 존재하지 않습니다.");
    }
}
