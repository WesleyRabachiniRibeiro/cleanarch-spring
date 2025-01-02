package br.com.cleanarch.application.repository;

import br.com.cleanarch.domain.product.ProductRepository;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products;

    public InMemoryProductRepository() {
        this.products = new HashMap<>();
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return Optional.ofNullable(this.products.get(Objects.requireNonNull(productId).getValue()));
    }

    @Override
    public List<Product> allProducts() {
        return new ArrayList<>(this.products.values());
    }

    @Override
    public Product save(Product product) {
        this.products.put(product.getProductId().getValue(), product);
        return product;
    }

    @Override
    public void deleteProductById(ProductId productId) {
        this.products.remove(productId.getValue());
    }

    @Override
    public void deleteAll() {
        this.products.clear();
    }
}
