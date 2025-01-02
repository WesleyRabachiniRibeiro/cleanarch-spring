package br.com.cleanarch.domain.product;

import br.com.cleanarch.domain.exceptions.ValidationException;

import java.math.BigDecimal;

public class Product {

    private ProductId productId;

    private String name;

    private Integer quantity;

    private BigDecimal price;

    private String description;

    private boolean isActive;

    public Product(final ProductId productId, final String name, final Integer quantity, final BigDecimal price, final String description, final boolean isActive) {
        if (productId == null) {
            throw new ValidationException("Invalid partnerId for Partner");
        }
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
    }

    public static Product newProduct(String name, Integer quantity, BigDecimal price, String description, boolean isActive) {
        return new Product(ProductId.unique(), name, quantity, price, description, isActive);
    }

    public void update(String name, int quantity, BigDecimal price, String description, boolean isActive) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }
}