package br.com.cleanarch.domain.product;

import br.com.cleanarch.domain.exceptions.ValidationException;

import java.util.UUID;

public class ProductId {

    private final String value;

    public ProductId(String value){
        if (value == null) {
            throw new ValidationException("Invalid value for CustomerId");
        }
        this.value = value;
    }

    public static ProductId unique() {
        return new ProductId(UUID.randomUUID().toString());
    }

    public static ProductId with(final String value) {
        try {
            return new ProductId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid value for CustomerId");
        }
    }

    public String getValue() {
        return value;
    }
}
