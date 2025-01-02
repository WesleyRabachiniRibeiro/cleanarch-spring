package br.com.cleanarch.infrastructure.usecases;

import br.com.cleanarch.application.product.GetProductByIdUseCase;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;
import br.com.cleanarch.infrastructure.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;


public class GetProductByIdUseCaseTest extends IntegrationTest {

    @Autowired
    private GetProductByIdUseCase useCase;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    void shouldReturnProductWhenProductExists() {
        Product productToSave = new Product(
                ProductId.unique(),
                "Smartphone",
                10,
                new BigDecimal("1500.00"),
                "Latest model",
                true
        );

        productRepository.save(productToSave);

        GetProductByIdUseCase.Input input = new GetProductByIdUseCase.Input(productToSave.getProductId().getValue());

        GetProductByIdUseCase.Output output = useCase.execute(input).get();

        Assertions.assertNotNull(output);
        Assertions.assertEquals(productToSave.getProductId().getValue(), output.getId());
        Assertions.assertEquals(productToSave.getName(), output.getName());
        Assertions.assertEquals(productToSave.getQuantity(), output.getQuantity());
        Assertions.assertEquals(productToSave.getPrice(), output.getPrice());
        Assertions.assertEquals(productToSave.getDescription(), output.getDescription());
        Assertions.assertTrue(output.isActive());
    }

    @Test
    void shouldReturnFalseWhenProductDoesNotExist() {
        GetProductByIdUseCase.Input input = new GetProductByIdUseCase.Input(ProductId.unique().getValue());

        Optional<GetProductByIdUseCase.Output> output = useCase.execute(input);

        Assertions.assertFalse(output.isPresent());
    }
}
