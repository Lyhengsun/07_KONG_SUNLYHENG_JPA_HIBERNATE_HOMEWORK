package com.api.product_mgmt.service;

import java.util.UUID;

import com.api.product_mgmt.dto.request.ProductRequest;
import com.api.product_mgmt.dto.response.PagedResponseList;
import com.api.product_mgmt.model.Product;

import jakarta.validation.constraints.Positive;

public interface ProductService {

    Product findById(UUID productId);

    Product save(ProductRequest request);

    Product update(UUID productId, ProductRequest request);

    Product delete(UUID productId);

    PagedResponseList<Product> findAll(Integer page, Integer size);
}
