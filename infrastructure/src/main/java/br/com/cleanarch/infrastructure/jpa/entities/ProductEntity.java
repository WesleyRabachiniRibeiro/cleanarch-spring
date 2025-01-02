package br.com.cleanarch.infrastructure.jpa.entities;

import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "Product")
@Table(name = "products")
public class ProductEntity {

    @Id
    private UUID id;

    private String name;

    private Integer quantity;

    private BigDecimal price;

    private String description;

    private boolean isActive;

    public ProductEntity() {
    }

    public ProductEntity(UUID id, String name, Integer quantity, BigDecimal price, String description, boolean isActive) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.isActive = isActive;
    }

    public static ProductEntity of(final Product product) {
        return new ProductEntity(
                UUID.fromString(product.getProductId().getValue()),
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getDescription(),
                product.isActive());
    }

    public Product toProduct() {
        return new Product(ProductId.with(this.id.toString()), this.name, this.quantity, this.price, this.description, this.isActive);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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
}
