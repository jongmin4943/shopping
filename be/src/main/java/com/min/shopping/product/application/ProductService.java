package com.min.shopping.product.application;

import com.min.shopping.brand.application.BrandProvider;
import com.min.shopping.brand.domain.Brand;
import com.min.shopping.common.Category;
import com.min.shopping.product.application.dto.ProductCreateRequest;
import com.min.shopping.product.application.dto.ProductModifyRequest;
import com.min.shopping.product.application.dto.ProductResponse;
import com.min.shopping.product.domain.Product;
import com.min.shopping.product.domain.ProductRepository;
import com.min.shopping.product.exception.ProductNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandProvider brandProvider;

    @Transactional
    public ProductResponse save(final ProductCreateRequest request) {
        final Brand brand = brandProvider.findById(request.getBrandId());

        final Product product = new Product(brand.getId(), request.getCategory(), request.getPrice());
        final Product saved = productRepository.save(product);

        updateBrandStatus(brand);

        return ProductResponse.from(saved);
    }

    @Transactional
    public void update(final Long id, final ProductModifyRequest request) {
        final Product product = findProductById(id);
        final Brand brand = brandProvider.findById(product.getBrandId());

        product.modify(request.getCategory(), request.getPrice());
        productRepository.save(product);

        updateBrandStatus(brand);
    }

    @Transactional
    public void delete(final Long id) {
        final Product product = findProductById(id);
        final Brand brand = brandProvider.findById(product.getBrandId());

        productRepository.delete(product);

        updateBrandStatus(brand);
    }

    // 이벤트 기반으로 할까 고민하다가 복잡성 증가 및 트랜잭션으로 묶기 위해 메서드로 분리
    private void updateBrandStatus(final Brand brand) {
        if (productRepository.countProductCategoryTypesByBrand(brand.getId()) >= Category.countTypes()) {
            brand.activate();
        } else {
            brand.inactivate();
        }
    }

    public ProductResponse findById(final Long id) {
        final Product product = findProductById(id);
        return ProductResponse.from(product);
    }

    private Product findProductById(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotExistException("상품이 존재하지 않습니다."));
    }
}
