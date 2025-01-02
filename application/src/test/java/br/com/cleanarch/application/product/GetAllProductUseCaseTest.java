package br.com.cleanarch.application.product;

import br.com.cleanarch.application.repository.InMemoryProductRepository;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class GetAllProductUseCaseTest {

    @Test
    @DisplayName("Should retrieve all products successfully")
    public void testGetAllProducts() {
        // Arrange
        ProductRepository inMemoryRepository = new InMemoryProductRepository();
        GetAllProductUseCase useCase = new GetAllProductUseCase(inMemoryRepository);

        Product product1 = new Product(
                ProductId.unique(),
                "Product 1",
                10,
                BigDecimal.valueOf(100.0),
                "Description 1",
                true
        );

        Product product2 = new Product(
                ProductId.unique(),
                "Product 2",
                5,
                BigDecimal.valueOf(200.0),
                "Description 2",
                false
        );

        inMemoryRepository.save(product1);
        inMemoryRepository.save(product2);

        List<GetAllProductUseCase.Output> outputs = useCase.execute();

        Assertions.assertEquals(2, outputs.size());
        GetAllProductUseCase.Output output1 = outputs.get(0);
        Assertions.assertEquals(product1.getProductId().getValue(), output1.getId());
        Assertions.assertEquals(product1.getName(), output1.getName());
        Assertions.assertEquals(product1.getQuantity(), output1.getQuantity());
        Assertions.assertEquals(product1.getPrice(), output1.getPrice());
        Assertions.assertEquals(product1.getDescription(), output1.getDescription());
        Assertions.assertTrue(output1.isActive());

        GetAllProductUseCase.Output output2 = outputs.get(1);
        Assertions.assertEquals(product2.getProductId().getValue(), output2.getId());
        Assertions.assertEquals(product2.getName(), output2.getName());
        Assertions.assertEquals(product2.getQuantity(), output2.getQuantity());
        Assertions.assertEquals(product2.getPrice(), output2.getPrice());
        Assertions.assertEquals(product2.getDescription(), output2.getDescription());
        Assertions.assertFalse(output2.isActive());
    }
}
