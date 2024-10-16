package com.min.shopping.product.presentation;

import com.min.shopping.product.application.ProductService;
import com.min.shopping.product.application.dto.ProductCreateRequest;
import com.min.shopping.product.application.dto.ProductModifyRequest;
import com.min.shopping.product.application.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid final ProductCreateRequest request) {
        final ProductResponse productResponse = productService.save(request);
        return ResponseEntity.created(URI.create("/products/" + productResponse.getId())).body(productResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProduct(@PathVariable("id") final Long id) {
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> modifyProduct(@PathVariable("id") final Long id, @RequestBody @Valid final ProductModifyRequest request) {
        productService.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("id") final Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
