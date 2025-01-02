package br.com.cleanarch.application.product;

import br.com.cleanarch.application.repository.InMemoryProductRepository;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class UpdateProductUseCaseTest {

    @Test
    public void testUpdateProduct() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        Product product = new Product(
                ProductId.with("123e4567-e89b-12d3-a456-426614174000"),
                "Old Product",
                10,
                BigDecimal.valueOf(100),
                "Old Description",
                true);
        productRepository.save(product);
        UpdateProductUseCase useCase = new UpdateProductUseCase(productRepository);
        UpdateProductUseCase.Input input = new UpdateProductUseCase.Input(
                "123e4567-e89b-12d3-a456-426614174000",
                "Updated Product",
                20,
                BigDecimal.valueOf(150),
                "Updated Description",
                false
        );

        UpdateProductUseCase.Output output = useCase.execute(input);

        Assertions.assertEquals("123e4567-e89b-12d3-a456-426614174000", output.getId());
        Assertions.assertEquals("Updated Product", output.getName());
        Assertions.assertEquals(20, output.getQuantity());
        Assertions.assertEquals(BigDecimal.valueOf(150), output.getPrice());
        Assertions.assertEquals("Updated Description", output.getDescription());
        Assertions.assertFalse(output.isActive());

        Product updatedProduct = productRepository.findById(ProductId.with("123e4567-e89b-12d3-a456-426614174000")).get();
        Assertions.assertEquals("Updated Product", updatedProduct.getName());
        Assertions.assertEquals(20, updatedProduct.getQuantity());
        Assertions.assertEquals(BigDecimal.valueOf(150), updatedProduct.getPrice());
        Assertions.assertEquals("Updated Description", updatedProduct.getDescription());
        Assertions.assertFalse(updatedProduct.isActive());
    }
}


