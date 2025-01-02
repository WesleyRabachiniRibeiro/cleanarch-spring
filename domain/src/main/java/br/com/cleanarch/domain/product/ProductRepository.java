package br.com.cleanarch.domain.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(ProductId productId);

    List<Product> allProducts();

    Product save(Product product);

    void deleteProductById(ProductId productId);

    void deleteAll();
}
