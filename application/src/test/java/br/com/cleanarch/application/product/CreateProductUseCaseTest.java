package br.com.cleanarch.application.product;

import br.com.cleanarch.application.repository.InMemoryProductRepository;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CreateProductUseCaseTest {

    private InMemoryProductRepository productRepository;
    private CreateProductUseCase createProductUseCase;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        createProductUseCase = new CreateProductUseCase(productRepository);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        CreateProductUseCase.Input input = new CreateProductUseCase.Input(
                "Smartphone",
                10,
                new BigDecimal("1500.00"),
                "Latest model",
                true
        );

        CreateProductUseCase.Output output = createProductUseCase.execute(input);

        assertNotNull(output.getId());
        assertEquals(input.getName(), output.name);
        assertEquals(input.getQuantity(), output.quantity);
        assertEquals(input.getPrice(), output.price);
        assertEquals(input.getDescription(), output.description);
        assertTrue(output.isActive);
    }

    @Test
    void shouldPassCorrectDataToRepository() {
        CreateProductUseCase.Input input = new CreateProductUseCase.Input(
                "Laptop",
                5,
                new BigDecimal("2500.00"),
                "High-end laptop",
                true
        );

        CreateProductUseCase.Output output = createProductUseCase.execute(input);

        Product result = productRepository.findById(ProductId.with(output.getId())).get();
        assertEquals(input.getName(), result.getName());
        assertEquals(input.getQuantity(), result.getQuantity());
        assertEquals(input.getPrice(), result.getPrice());
        assertEquals(input.getDescription(), result.getDescription());
        assertTrue(result.isActive());
    }

    @Test
    void shouldHandleNullDescription() {
        CreateProductUseCase.Input input = new CreateProductUseCase.Input(
                "Keyboard",
                20,
                new BigDecimal("300.00"),
                null,
                true
        );

        CreateProductUseCase.Output output = createProductUseCase.execute(input);

        assertNull(output.description);
    }
}
