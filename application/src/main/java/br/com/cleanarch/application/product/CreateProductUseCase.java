package br.com.cleanarch.application.product;

import br.com.cleanarch.application.UseCase;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductRepository;

import java.math.BigDecimal;

public class CreateProductUseCase extends UseCase<CreateProductUseCase.Input, CreateProductUseCase.Output> {

    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Output execute(final Input input) {
        Product product = productRepository.save(
                Product.newProduct(
                        input.getName(),
                        input.getQuantity(),
                        input.getPrice(),
                        input.getDescription(),
                        input.isActive()));

        return new Output(
                product.getProductId().getValue(),
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getDescription(),
                product.isActive()
        );
    }

    public static class Input {
        private final String name;
        private final int quantity;
        private final BigDecimal price;
        private final String description;
        private final boolean isActive;

        public Input(final String name, final int quantity, final BigDecimal price, final String description, final boolean isActive) {
            this.name = name;
            this.quantity = quantity;
            this.price = price;
            this.description = description;
            this.isActive = isActive;
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

    public static class Output {
        String id;
        String name;
        int quantity;
        BigDecimal price;
        String description;
        boolean isActive;

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
