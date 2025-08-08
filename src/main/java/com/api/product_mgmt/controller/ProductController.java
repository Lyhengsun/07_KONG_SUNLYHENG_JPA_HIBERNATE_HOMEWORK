package com.api.product_mgmt.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.product_mgmt.dto.request.ProductRequest;
import com.api.product_mgmt.dto.response.ApiResponse;
import com.api.product_mgmt.dto.response.BaseResponse;
import com.api.product_mgmt.dto.response.PagedResponseList;
import com.api.product_mgmt.model.Product;
import com.api.product_mgmt.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController extends BaseResponse {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponseList<Product>>> getAllProducts(
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        PagedResponseList<Product> productList = productService.findAll(page, size);
        String message = "Fetch products successfully";
        if (productList.getItems().isEmpty()) {
            message = "No Record";
        }
        return responseEntity(message, HttpStatus.OK, productList);
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ApiResponse<Product>> findProductById(
            @PathVariable("product-id") UUID productId) {
        Product foundProduct = productService.findById(productId);
        return responseEntity("Found product with ID: \"" + productId + "\" successfully", HttpStatus.OK, foundProduct);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> saveProduct(@Valid @RequestBody ProductRequest request) {
        Product savedProduct = productService.save(request);
        return responseEntity("Saved new product successfully", HttpStatus.CREATED, savedProduct);
    }

    @PutMapping("/{product-id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable("product-id") UUID productId,
            @Valid @RequestBody ProductRequest request) {
        Product updatedProduct = productService.update(productId, request);
        return responseEntity("Updated product with ID: `" + productId + "` successfully", HttpStatus.OK,
                updatedProduct);
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<ApiResponse<Product>> deleteProduct(@PathVariable("product-id") UUID productId) {
        Product deletedProduct = productService.delete(productId);
        return responseEntity("Deleted product with ID: `" + productId + "` successfully", HttpStatus.OK,
                deletedProduct);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponseList<Product>>> searchProductByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size) {

        PagedResponseList<Product> productList = productService.findByName(name, page, size);
        String message = "Find product by name successfully";
        if (productList.getItems().isEmpty()) {
            message = "No Record Found";
        }
        return responseEntity(message, HttpStatus.OK, productList);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<PagedResponseList<Product>>> searchProductWithLowerQuantity(
            @RequestParam @Positive Integer quantity,
            @RequestParam(defaultValue = "1") @Positive Integer page,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        
        PagedResponseList<Product> productList = productService.findByLowerQuantity(quantity, page, size);
        String message = "Find product with lower quantity successfully";
        if (productList.getItems().isEmpty()) {
            message = "No Record Found";
        }
        return responseEntity(message, HttpStatus.OK, productList);
    }
}