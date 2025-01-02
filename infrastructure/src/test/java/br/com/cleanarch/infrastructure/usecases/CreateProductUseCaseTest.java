package br.com.cleanarch.infrastructure.usecases;

import br.com.cleanarch.application.product.CreateProductUseCase;
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

public class CreateProductUseCaseTest extends IntegrationTest {

    @Autowired
    private CreateProductUseCase useCase;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create a product successfully")
    public void testCreateProduct() {
        final String expectedName = "Laptop";
        final int expectedQuantity = 5;
        final BigDecimal expectedPrice = new BigDecimal("4500.00");
        final String expectedDescription = "High performance";
        final boolean expectedIsActive = true;

        CreateProductUseCase.Input input = new CreateProductUseCase.Input(
                expectedName, expectedQuantity, expectedPrice, expectedDescription, expectedIsActive
        );

        CreateProductUseCase.Output output = useCase.execute(input);

        Assertions.assertNotNull(output.getId(), "The product ID should not be null");

        Optional<Product> savedProduct = productRepository.findById(ProductId.with(output.getId()));
        Assertions.assertTrue(savedProduct.isPresent(), "The saved product should be present in the repository");

        Product product = savedProduct.get();
        Assertions.assertEquals(expectedName, product.getName(), "The saved product name should match");
        Assertions.assertEquals(expectedQuantity, product.getQuantity(), "The saved product quantity should match");
        Assertions.assertEquals(expectedPrice, product.getPrice(), "The saved product price should match");
        Assertions.assertEquals(expectedDescription, product.getDescription(), "The saved product description should match");
        Assertions.assertTrue(product.isActive(), "The saved product's active status should match");
    }
}
