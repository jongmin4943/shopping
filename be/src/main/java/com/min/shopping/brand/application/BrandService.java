package com.min.shopping.brand.application;

import com.min.shopping.brand.application.dto.BrandCreateRequest;
import com.min.shopping.brand.application.dto.BrandModifyRequest;
import com.min.shopping.brand.application.dto.BrandResponse;
import com.min.shopping.brand.domain.Brand;
import com.min.shopping.brand.domain.BrandRepository;
import com.min.shopping.brand.exception.BrandNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandResponse save(final BrandCreateRequest request) {
        final Brand brand = new Brand(request.getName());

        final Brand saved = brandRepository.save(brand);

        return BrandResponse.from(saved);
    }

    public List<BrandResponse> findAll() {
        return brandRepository.findAll()
                .stream()
                .map(BrandResponse::from)
                .toList();
    }

    public void update(final Long id, final BrandModifyRequest request) {
        final Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotExistException("브랜드가 존재하지 않습니다."));

        brand.modifyName(request.getName());

        brandRepository.save(brand);
    }

    public void delete(final Long id) {

    }

}
