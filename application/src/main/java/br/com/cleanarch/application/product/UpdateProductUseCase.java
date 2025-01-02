package br.com.cleanarch.application.product;

import br.com.cleanarch.application.UseCase;
import br.com.cleanarch.domain.exceptions.ValidationException;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class UpdateProductUseCase extends UseCase<UpdateProductUseCase.Input, UpdateProductUseCase.Output> {

    private final ProductRepository productRepository;

    public UpdateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Output execute(Input input) {
        Optional<Product> existingProduct = productRepository.findById(ProductId.with(input.id));

        if (!existingProduct.isPresent()) {
            throw new ValidationException("Product not found");
        }

        Product product = existingProduct.get();

        product.update(input.name, input.quantity, input.price, input.description, input.isActive);

        productRepository.save(product);

        return new Output(
                product.getProductId().getValue(),
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getDescription(),
                product.isActive());
    }

    public static class Input {
        private final String id;
        private final String name;
        private final int quantity;
        private final BigDecimal price;
        private final String description;
        private final boolean isActive;

        public Input(String id, String name, int quantity, BigDecimal price, String description, boolean isActive) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.description = description;
            this.isActive = isActive;
        }
    }

    public static class Output {
        private final String id;
        private final String name;
        private final int quantity;
        private final BigDecimal price;
        private final String description;
        private final boolean isActive;

        public Output(String id, String name, int quantity, BigDecimal price, String description, boolean isActive) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.description = description;
            this.isActive = isActive;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
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
}
