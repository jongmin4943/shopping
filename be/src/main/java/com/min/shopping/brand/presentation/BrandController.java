package com.min.shopping.brand.presentation;

import com.min.shopping.brand.application.BrandService;
import com.min.shopping.brand.application.dto.BrandCreateRequest;
import com.min.shopping.brand.application.dto.BrandModifyRequest;
import com.min.shopping.brand.application.dto.BrandResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(final BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    public ResponseEntity<BrandResponse> createBrand(@RequestBody @Valid final BrandCreateRequest request) {
        final BrandResponse brandResponse = brandService.save(request);
        return ResponseEntity.created(URI.create("/brands/" + brandResponse.getId())).body(brandResponse);
    }

    @GetMapping
    public ResponseEntity<List<BrandResponse>> findBrands() {
        return ResponseEntity.ok().body(brandService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> modifyBrand(@PathVariable("id") final Long id, @RequestBody @Valid final BrandModifyRequest request) {
        brandService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeBrand(@PathVariable("id") final Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
