package br.com.cleanarch.infrastructure.usecases;

import br.com.cleanarch.application.product.GetAllProductUseCase;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;
import br.com.cleanarch.infrastructure.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

public class GetAllProductUseCaseTest extends IntegrationTest {

    @Autowired
    private GetAllProductUseCase useCase;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    void shouldReturnAllProductsWhenProductsExist() {
        // Arrange
        Product product1 = new Product(
                ProductId.unique(),
                "Smartphone",
                10,
                new BigDecimal("1500.00"),
                "Latest model",
                true
        );

        Product product2 = new Product(
                ProductId.unique(),
                "Laptop",
                5,
                new BigDecimal("3500.00"),
                "High-performance laptop",
                true
        );

        productRepository.save(product1);
        productRepository.save(product2);

        List<GetAllProductUseCase.Output> outputs = useCase.execute();

        Assertions.assertNotNull(outputs);
        Assertions.assertEquals(2, outputs.size());
        Assertions.assertTrue(outputs.stream()
                .anyMatch(output -> output.getId().equals(product1.getProductId().getValue())));
        Assertions.assertTrue(outputs.stream()
                .anyMatch(output -> output.getId().equals(product2.getProductId().getValue())));
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsExist() {
        List<GetAllProductUseCase.Output> outputs = useCase.execute();
        Assertions.assertNotNull(outputs);
        Assertions.assertTrue(outputs.isEmpty());
    }
}
