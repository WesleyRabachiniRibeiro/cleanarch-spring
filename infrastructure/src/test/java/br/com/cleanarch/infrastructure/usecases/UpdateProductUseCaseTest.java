package br.com.cleanarch.infrastructure.usecases;

import br.com.cleanarch.application.product.UpdateProductUseCase;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;
import br.com.cleanarch.infrastructure.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class UpdateProductUseCaseTest extends IntegrationTest {

    @Autowired
    private UpdateProductUseCase useCase;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("Should update a product successfully")
    public void testUpdateProduct() {
        final String initialName = "Old Laptop";
        final int initialQuantity = 5;
        final BigDecimal initialPrice = new BigDecimal("4500.00");
        final String initialDescription = "Old performance laptop";
        final boolean initialIsActive = true;

        Product product = new Product(
                ProductId.with("123e4567-e89b-12d3-a456-426614174000"),
                initialName,
                initialQuantity,
                initialPrice,
                initialDescription,
                initialIsActive
        );

        productRepository.save(product);

        final String updatedName = "Updated Laptop";
        final int updatedQuantity = 10;
        final BigDecimal updatedPrice = new BigDecimal("5000.00");
        final String updatedDescription = "Updated performance laptop";
        final boolean updatedIsActive = false;

        UpdateProductUseCase.Input input =
                new UpdateProductUseCase.Input(
                        "123e4567-e89b-12d3-a456-426614174000",
                        updatedName,
                        updatedQuantity,
                        updatedPrice,
                        updatedDescription,
                        updatedIsActive
                );

        UpdateProductUseCase.Output output = useCase.execute(input);

        Assertions.assertEquals("123e4567-e89b-12d3-a456-426614174000", output.getId());
        Assertions.assertEquals(updatedName, output.getName());
        Assertions.assertEquals(updatedQuantity, output.getQuantity());
        Assertions.assertEquals(updatedPrice, output.getPrice());
        Assertions.assertEquals(updatedDescription, output.getDescription());
        Assertions.assertFalse(output.isActive());

        Product updatedProduct = productRepository.findById(ProductId.with("123e4567-e89b-12d3-a456-426614174000")).get();
        Assertions.assertEquals(updatedName, updatedProduct.getName());
        Assertions.assertEquals(updatedQuantity, updatedProduct.getQuantity());
        Assertions.assertEquals(updatedPrice, updatedProduct.getPrice());
        Assertions.assertEquals(updatedDescription, updatedProduct.getDescription());
        Assertions.assertFalse(updatedProduct.isActive());
    }
}
