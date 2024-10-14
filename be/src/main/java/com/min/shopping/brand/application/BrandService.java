package com.min.shopping.brand.application;

import com.min.shopping.brand.application.dto.BrandCreateRequest;
import com.min.shopping.brand.application.dto.BrandModifyRequest;
import com.min.shopping.brand.application.dto.BrandResponse;
import com.min.shopping.brand.domain.Brand;
import com.min.shopping.brand.domain.BrandRepository;
import com.min.shopping.brand.exception.BrandCreateException;
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

        checkDuplicateName(brand.getName());

        final Brand saved = brandRepository.save(brand);

        return BrandResponse.from(saved);
    }

    public void update(final Long id, final BrandModifyRequest request) {
        final Brand brand = findById(id);

        checkDuplicateName(request.getName());

        brand.modifyName(request.getName());

        brandRepository.save(brand);
    }

    private void checkDuplicateName(final String name) {
        if (brandRepository.existsByName(name)) {
            throw new BrandCreateException("이미 존재하는 브랜드 이름입니다.");
        }
    }

    public void delete(final Long id) {
        final Brand brand = findById(id);

        brandRepository.delete(brand);
    }

    public List<BrandResponse> findAll() {
        return brandRepository.findAll()
                .stream()
                .map(BrandResponse::from)
                .toList();
    }

    private Brand findById(final Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new BrandNotExistException("브랜드가 존재하지 않습니다."));
    }

}
