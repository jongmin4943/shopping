package com.min.shopping.product.application;

import com.min.shopping.common.Category;
import com.min.shopping.product.application.dto.ProductLowestPriceForSingleBrandResponse;
import com.min.shopping.product.application.dto.ProductLowestPricesResponse;
import com.min.shopping.product.application.dto.ProductPriceRangeForCategoryResponse;
import com.min.shopping.product.application.dto.ProductResponse;
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
                .orElseThrow();
    }

    public ProductLowestPriceForSingleBrandResponse getLowestTotalPriceProductsForSingleBrand() {
        final ProductLowestPriceForSingleBrandResponse productLowestPriceForSingleBrandResponse = productDao.findLowestTotalPriceSingleBrandId().orElseThrow();
        final Long brandId = productLowestPriceForSingleBrandResponse.getBrandId();

        final List<ProductResponse> lowestPriceProductByCategoryForBrand = productDao.getLowestPriceProductByCategoryForBrand(brandId);
        productLowestPriceForSingleBrandResponse.bindProducts(lowestPriceProductByCategoryForBrand);

        return productLowestPriceForSingleBrandResponse;
    }

    public ProductPriceRangeForCategoryResponse getPriceRangeForCategory(final Category category) {
        return productDao.getPriceRangeForCategory(category).orElseThrow();
    }
}
