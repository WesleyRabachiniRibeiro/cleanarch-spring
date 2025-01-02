package br.com.cleanarch.application.product;

import br.com.cleanarch.application.NullaryUserCase;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class GetAllProductUseCase extends NullaryUserCase<List<GetAllProductUseCase.Output>> {

    private final ProductRepository productRepository;

    public GetAllProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Output> execute() {
        return productRepository.allProducts().stream().map(Output::fromProduct).collect(Collectors.toList());
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

        public static Output fromProduct(Product product) {
            return new Output(
                    product.getProductId().getValue(),
                    product.getName(),
                    product.getQuantity(),
                    product.getPrice(),
                    product.getDescription(),
                    product.isActive()
            );
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
