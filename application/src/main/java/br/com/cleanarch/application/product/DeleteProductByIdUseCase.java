package br.com.cleanarch.application.product;

import br.com.cleanarch.application.UnitUseCase;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;

public class DeleteProductByIdUseCase extends UnitUseCase<DeleteProductByIdUseCase.Input> {

    private final ProductRepository productRepository;

    public DeleteProductByIdUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void execute(Input input) {
        productRepository.deleteProductById(ProductId.with(input.getId()));
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
}
