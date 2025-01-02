package br.com.cleanarch.domain.product;

import br.com.cleanarch.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void shouldCreateProductSuccessfully() {
        String name = "Laptop";
        Integer quantity = 10;
        BigDecimal price = new BigDecimal("2500.00");
        String description = "High-end gaming laptop";
        boolean isActive = true;

        Product product = Product.newProduct(name, quantity, price, description, isActive);

        assertNotNull(product.getProductId());
        assertEquals(name, product.getName());
        assertEquals(quantity, product.getQuantity());
        assertEquals(price, product.getPrice());
        assertEquals(description, product.getDescription());
        assertTrue(product.isActive());
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsNull() {
        String name = "Smartphone";
        Integer quantity = 5;
        BigDecimal price = new BigDecimal("1500.00");
        String description = "Latest model smartphone";
        boolean isActive = true;

        ValidationException exception = assertThrows(ValidationException.class, () ->
                new Product(null, name, quantity, price, description, isActive)
        );
        assertEquals("Invalid partnerId for Partner", exception.getMessage());
    }

    @Test
    void shouldCreateProductWithZeroQuantity() {
        String name = "Headphones";
        Integer quantity = 0;
        BigDecimal price = new BigDecimal("200.00");
        String description = "Wireless noise-cancelling headphones";
        boolean isActive = true;

        Product product = Product.newProduct(name, quantity, price, description, isActive);

        assertEquals(0, product.getQuantity());
        assertEquals(name, product.getName());
    }

    @Test
    void shouldCreateInactiveProduct() {
        String name = "Monitor";
        Integer quantity = 2;
        BigDecimal price = new BigDecimal("800.00");
        String description = "4K Ultra HD Monitor";
        boolean isActive = false;

        Product product = Product.newProduct(name, quantity, price, description, isActive);

        assertFalse(product.isActive());
    }

    @Test
    void shouldHandleNullDescription() {
        String name = "Keyboard";
        Integer quantity = 15;
        BigDecimal price = new BigDecimal("300.00");
        String description = null;
        boolean isActive = true;

        Product product = Product.newProduct(name, quantity, price, description, isActive);

        assertNull(product.getDescription());
        assertEquals(name, product.getName());
    }

    @Test
    void shouldHandleNegativePrice() {
        String name = "Mouse";
        Integer quantity = 20;
        BigDecimal price = new BigDecimal("-50.00");
        String description = "Ergonomic wireless mouse";
        boolean isActive = true;

        Product product = Product.newProduct(name, quantity, price, description, isActive);

        assertEquals(new BigDecimal("-50.00"), product.getPrice());
    }

    @Test
    public void shouldUpdateProductFields() {
        Product product = new Product(ProductId.unique(), "Old Product", 10, BigDecimal.valueOf(100), "Old Description", true);

        product.update("Updated Product", 20, BigDecimal.valueOf(150), "Updated Description", false);

        assertEquals("Updated Product", product.getName());
        assertEquals(20, product.getQuantity());
        assertEquals(BigDecimal.valueOf(150), product.getPrice());
        assertEquals("Updated Description", product.getDescription());
        assertFalse(product.isActive());
    }
}
