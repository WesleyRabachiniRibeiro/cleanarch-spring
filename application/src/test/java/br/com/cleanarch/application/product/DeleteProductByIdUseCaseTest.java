package br.com.cleanarch.application.product;

import br.com.cleanarch.application.repository.InMemoryProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;

public class DeleteProductByIdUseCaseTest {

    @Test
    @DisplayName("Should delete a product by ID")
    public void testDeleteProductById() {
        final String expectedId = "123e4567-e89b-12d3-a456-426614174000";

        // Criando um produto e salvando no repositório InMemory
        final Product product = new Product(
                ProductId.with(expectedId),
                "Product Name",
                10,
                BigDecimal.valueOf(99.99),
                "Product Description",
                true
        );

        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        productRepository.save(product);

        final DeleteProductByIdUseCase.Input input = new DeleteProductByIdUseCase.Input(expectedId);
        final DeleteProductByIdUseCase useCase = new DeleteProductByIdUseCase(productRepository);

        useCase.execute(input);

        Assertions.assertFalse(productRepository.findById(ProductId.with(expectedId)).isPresent());
    }
}
