package br.com.cleanarch.infrastructure.dtos;

import java.math.BigDecimal;

public class UpdateProduct {
    private String name;
    private int quantity;
    private BigDecimal price;
    private String description;
    private boolean isActive;

    public UpdateProduct(String name, int quantity, BigDecimal price, String description, boolean isActive) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "NewProduct{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
