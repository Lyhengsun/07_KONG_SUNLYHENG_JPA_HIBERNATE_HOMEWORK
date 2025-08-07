package com.api.product_mgmt.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.api.product_mgmt.dto.request.ProductRequest;
import com.api.product_mgmt.dto.response.PagedResponseList;
import com.api.product_mgmt.dto.response.PaginationInfo;
import com.api.product_mgmt.model.Product;
import com.api.product_mgmt.repository.ProductRepository;
import com.api.product_mgmt.utils.ServiceValicationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product findById(UUID productId) {
        Product foundProduct = productRepository.findById(productId);
        ServiceValicationUtils.notFoundValidation(foundProduct == null,
                "Product with ID: `" + productId + "` doesn't exist");
        return foundProduct;
    }

    @Override
    public Product save(ProductRequest request) {
        return productRepository.save(request);
    }

    @Override
    public Product update(UUID productId, ProductRequest request) {
        Product foundProduct = productRepository.findById(productId);
        ServiceValicationUtils.notFoundValidation(foundProduct == null,
                "Product with ID: `" + productId + "` doesn't exist");
        return productRepository.update(productId, request);
    }

    @Override
    public Product delete(UUID productId) {
        Product foundProduct = productRepository.findById(productId);
        ServiceValicationUtils.notFoundValidation(foundProduct == null,
                "Product with ID: `" + productId + "` doesn't exist");
        return productRepository.delete(productId);
    }

    @Override
    public PagedResponseList<Product> findAll(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<Product> products = productRepository.findAll(offset, size);
        PaginationInfo paginationInfo = new PaginationInfo(page, size, productRepository.countAllProducts());
        return new PagedResponseList<>(products, paginationInfo);
    }

    @Override
    public PagedResponseList<Product> findByName(String searchName, Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<Product> products = productRepository.findByName(searchName, offset, size);
        PaginationInfo paginationInfo = new PaginationInfo(page, size, productRepository.countAllProductByName(searchName));
        return new PagedResponseList<>(products, paginationInfo);
    }

    @Override
    public PagedResponseList<Product> findByLowerQuantity(Integer quantity, Integer page, Integer size) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByLowerQuantity'");
    }

}
