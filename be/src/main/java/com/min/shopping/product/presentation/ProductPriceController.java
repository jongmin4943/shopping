package com.min.shopping.product.presentation;

import com.min.shopping.common.Category;
import com.min.shopping.product.application.ProductPriceService;
import com.min.shopping.product.application.dto.ProductLowestPriceForSingleBrandResponse;
import com.min.shopping.product.application.dto.ProductLowestPricesResponse;
import com.min.shopping.product.application.dto.ProductPriceRangeForCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductPriceController {

    private final ProductPriceService productPriceService;

    @GetMapping("/lowest-prices")
    public ResponseEntity<ProductLowestPricesResponse> getLowestPricesByCategory() {
        return ResponseEntity.ok(productPriceService.getLowestPriceProductsByCategory());
    }

    @GetMapping("/lowest-prices-for-single-brand")
    public ResponseEntity<ProductLowestPriceForSingleBrandResponse> getLowestTotalPriceForSingleBrand() {
        return ResponseEntity.ok(productPriceService.getLowestTotalPriceProductsForSingleBrand());
    }

    @GetMapping("/price-range/{category}")
    public ResponseEntity<ProductPriceRangeForCategoryResponse> getPriceRangeForCategory(@PathVariable final Category category) {
        return ResponseEntity.ok(productPriceService.getPriceRangeForCategory(category));
    }

}
