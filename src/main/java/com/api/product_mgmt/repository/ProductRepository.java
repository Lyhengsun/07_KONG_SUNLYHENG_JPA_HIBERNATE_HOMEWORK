package com.api.product_mgmt.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.product_mgmt.dto.request.ProductRequest;
import com.api.product_mgmt.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class ProductRepository {
    @PersistenceContext
    private EntityManager em;

    public Product findById(UUID productId) {
        Product foundProduct = em.find(Product.class, productId);
        return foundProduct;
    }

    public Product save(ProductRequest request) {
        Product newProduct = Product.builder().name(request.getName()).price(request.getPrice())
                .quantity(request.getQuantity()).build();
        em.persist(newProduct);
        return newProduct;
    }

    public Product update(UUID productId, ProductRequest request) {
        Product foundProduct = findById(productId);
        if (foundProduct != null) {
            em.detach(foundProduct);
            foundProduct.setName(request.getName());
            foundProduct.setPrice(request.getPrice());
            foundProduct.setQuantity(request.getQuantity());
            em.merge(foundProduct);
        }
        return foundProduct;
    }

    public Product delete(UUID productId) {
        Product foundProduct = findById(productId);
        if (foundProduct != null) {
            em.remove(em.merge(foundProduct));
        }
        return foundProduct;
    }

    public List<Product> findAll(Integer offset, Integer limit) {
        List<Product> products = em.createQuery("SELECT p FROM products p", Product.class).setFirstResult(offset)
                .setMaxResults(limit).getResultList();
        return products;
    }

    public Integer countAllProducts() {
        Long productCount = em.createQuery("SELECT COUNT(p.id) FROM products p", Long.class).getSingleResult();
        return productCount.intValue();
    }

    public List<Product> findByName(String searchName, Integer offset, Integer limit) {
        TypedQuery<Product> query = em
                .createQuery("SELECT p FROM products p WHERE p.name LIKE CONCAT('%',:searchKeyword,'%')", Product.class)
                .setFirstResult(offset).setMaxResults(limit);
        query.setParameter("searchKeyword", searchName);
        return query.getResultList();
    }

    public Integer countAllProductByName(String searchName) {
        Long productCount = em
                .createQuery("SELECT COUNT(p.id) FROM products p WHERE p.name LIKE CONCAT('%',:searchKeyword,'%')",
                        Long.class)
                .setParameter("searchKeyword", searchName)
                .getSingleResult();
        return productCount.intValue();
    }
}
