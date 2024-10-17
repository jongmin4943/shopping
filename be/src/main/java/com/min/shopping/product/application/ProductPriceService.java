package com.min.shopping.product.application;

import com.min.shopping.common.Category;
import com.min.shopping.product.application.dto.ProductLowestPriceForSingleBrandResponse;
import com.min.shopping.product.application.dto.ProductLowestPricesResponse;
import com.min.shopping.product.application.dto.ProductPriceRangeForCategoryResponse;
import com.min.shopping.product.application.dto.ProductResponse;
import com.min.shopping.product.exception.ProductNotExistException;
import com.min.shopping.product.exception.ProductPriceNotFoundException;
import com.min.shopping.product.infra.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPriceService {

    private final ProductDao productDao;

    public ProductLowestPricesResponse getLowestPriceProductsByCategory() {
        return productDao.findLowestPriceProductsByCategory()
                .orElseThrow(() -> new ProductPriceNotFoundException("카테고리 별로 최저가격인 브랜드와 가격의 조회 결과가 없습니다."));
    }

    public ProductLowestPriceForSingleBrandResponse getLowestTotalPriceProductsForSingleBrand() {
        final ProductLowestPriceForSingleBrandResponse productLowestPriceForSingleBrandResponse =
                productDao.findLowestTotalPriceSingleBrandId()
                        .orElseThrow(() -> new ProductPriceNotFoundException("단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드의 조회 결과가 없습니다."));
        final Long brandId = productLowestPriceForSingleBrandResponse.getBrandId();

        final List<ProductResponse> lowestPriceProductByCategoryForBrand = productDao.getLowestPriceProductByCategoryForBrand(brandId);
        productLowestPriceForSingleBrandResponse.bindProducts(lowestPriceProductByCategoryForBrand);

        return productLowestPriceForSingleBrandResponse;
    }

    public ProductPriceRangeForCategoryResponse getPriceRangeForCategory(final Category category) {
        return productDao.getPriceRangeForCategory(category)
                .orElseThrow(() -> new ProductNotExistException("특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드의 조회 결과가 없습니다."));
    }
}
