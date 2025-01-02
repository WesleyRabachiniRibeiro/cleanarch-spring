package br.com.cleanarch.infrastructure.configurations;

import br.com.cleanarch.application.product.*;
import br.com.cleanarch.domain.product.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UseCaseConfig {
    private final ProductRepository productRepository;

    public UseCaseConfig(ProductRepository productRepository) {
        this.productRepository = Objects.requireNonNull(productRepository);
    }

    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new CreateProductUseCase(productRepository);
    }

    @Bean
    public GetProductByIdUseCase getProductByIdUseCase() {
        return new GetProductByIdUseCase(productRepository);
    }

    @Bean
    public DeleteProductByIdUseCase deleteProductByIdUseCase() {
        return new DeleteProductByIdUseCase(productRepository);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new UpdateProductUseCase(productRepository);
    }

    @Bean
    public GetAllProductUseCase getAllProductUserCase() {
        return new GetAllProductUseCase(productRepository);
    }
}
