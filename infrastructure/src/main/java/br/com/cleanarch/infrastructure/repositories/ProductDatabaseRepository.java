package br.com.cleanarch.infrastructure.repositories;

import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;
import br.com.cleanarch.infrastructure.jpa.entities.ProductEntity;
import br.com.cleanarch.infrastructure.jpa.repositories.ProductJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class ProductDatabaseRepository implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductDatabaseRepository(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        Objects.requireNonNull(productId, "id cannot be null");
        return this.productJpaRepository.findById(UUID.fromString(productId.getValue())).map(ProductEntity::toProduct);
    }

    @Override
    public List<Product> allProducts() {
        return StreamSupport.stream(productJpaRepository.findAll().spliterator(), false)
                .map(ProductEntity::toProduct)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return this.productJpaRepository.save(ProductEntity.of(product)).toProduct();
    }

    @Override
    @Transactional
    public void deleteProductById(ProductId productId) {
        this.productJpaRepository.deleteById(UUID.fromString(productId.getValue()));
    }

    @Override
    public void deleteAll() {
        this.productJpaRepository.deleteAll();
    }
}
