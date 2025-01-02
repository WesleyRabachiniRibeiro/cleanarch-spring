package br.com.cleanarch.application.product;

import br.com.cleanarch.application.UseCase;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class GetProductByIdUseCase extends UseCase<GetProductByIdUseCase.Input, Optional<GetProductByIdUseCase.Output>> {

    private final ProductRepository productRepository;

    public GetProductByIdUseCase(final ProductRepository productRepository) {
        this.productRepository = Objects.requireNonNull(productRepository);
    }

    @Override
    public Optional<Output> execute(final Input input) {
        return productRepository.findById(ProductId.with(input.getId()))
                .map(product -> new Output(
                        product.getProductId().getValue(),
                        product.getName(),
                        product.getQuantity(),
                        product.getPrice(),
                        product.getDescription(),
                        product.isActive()));
    }

    public static class Input {
        private String id;

        public Input(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
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
