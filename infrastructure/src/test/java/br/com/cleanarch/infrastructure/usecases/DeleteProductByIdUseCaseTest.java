package br.com.cleanarch.infrastructure.usecases;

import br.com.cleanarch.application.product.DeleteProductByIdUseCase;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;
import br.com.cleanarch.infrastructure.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

public class DeleteProductByIdUseCaseTest extends IntegrationTest {

    @Autowired
    private DeleteProductByIdUseCase useCase;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Should delete a product by ID successfully")
    public void testDeleteProductById() {
        final String expectedName = "Laptop";
        final int expectedQuantity = 5;
        final BigDecimal expectedPrice = new BigDecimal("4500.00");
        final String expectedDescription = "High performance";
        final boolean expectedIsActive = true;

        Product product = new Product(
                ProductId.with("123e4567-e89b-12d3-a456-426614174000"),
                expectedName,
                expectedQuantity,
                expectedPrice,
                expectedDescription,
                expectedIsActive
        );

        productRepository.save(product);

        Optional<Product> savedProduct = productRepository.findById(ProductId.with("123e4567-e89b-12d3-a456-426614174000"));
        Assertions.assertTrue(savedProduct.isPresent());

        DeleteProductByIdUseCase.Input input = new DeleteProductByIdUseCase.Input("123e4567-e89b-12d3-a456-426614174000");

        useCase.execute(input);

        Optional<Product> deletedProduct = productRepository.findById(ProductId.with("123e4567-e89b-12d3-a456-426614174000"));
        Assertions.assertFalse(deletedProduct.isPresent());
    }

}
