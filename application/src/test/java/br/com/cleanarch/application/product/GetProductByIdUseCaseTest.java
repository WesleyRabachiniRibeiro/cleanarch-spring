package br.com.cleanarch.application.product;

import br.com.cleanarch.application.repository.InMemoryProductRepository;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class GetProductByIdUseCaseTest {

    @Test
    @DisplayName("Should get a product by ID")
    public void testGetById() {
        final String expectedId = "123e4567-e89b-12d3-a456-426614174000";
        final String expectedName = "Product Name";
        final int expectedQuantity = 10;
        final BigDecimal expectedPrice = BigDecimal.valueOf(99.99);
        final String expectedDescription = "Product Description";
        final boolean expectedIsActive = true;

        final ProductRepository productRepository = new InMemoryProductRepository();

        final Product product = new Product(
                ProductId.with(expectedId),
                expectedName,
                expectedQuantity,
                expectedPrice,
                expectedDescription,
                expectedIsActive
        );
        productRepository.save(product);

        final GetProductByIdUseCase.Input input = new GetProductByIdUseCase.Input(expectedId);
        final GetProductByIdUseCase useCase = new GetProductByIdUseCase(productRepository);
        final GetProductByIdUseCase.Output output = useCase.execute(input).get();

        Assertions.assertEquals(expectedId, output.getId());
        Assertions.assertEquals(expectedName, output.getName());
        Assertions.assertEquals(expectedQuantity, output.getQuantity());
        Assertions.assertEquals(expectedPrice, output.getPrice());
        Assertions.assertEquals(expectedDescription, output.getDescription());
        Assertions.assertEquals(expectedIsActive, output.isActive());
        Assertions.assertTrue(output.isActive());
    }
}
