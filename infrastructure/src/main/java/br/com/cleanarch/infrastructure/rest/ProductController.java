package br.com.cleanarch.infrastructure.rest;

import br.com.cleanarch.application.product.*;
import br.com.cleanarch.domain.exceptions.ValidationException;
import br.com.cleanarch.infrastructure.dtos.NewProduct;
import br.com.cleanarch.infrastructure.dtos.UpdateProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final CreateProductUseCase createProductUseCase;

    private final GetProductByIdUseCase getProductByIdUseCase;

    private final DeleteProductByIdUseCase deleteProductByIdUseCase;

    private final UpdateProductUseCase updateProductUseCase;

    private final GetAllProductUseCase getAllProductUseCase;

    public ProductController(
            CreateProductUseCase createProductUseCase,
            GetProductByIdUseCase getProductByIdUseCase,
            DeleteProductByIdUseCase deleteProductByIdUseCase,
            UpdateProductUseCase updateProductUseCase,
            GetAllProductUseCase getAllProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.deleteProductByIdUseCase = deleteProductByIdUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.getAllProductUseCase = getAllProductUseCase;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NewProduct newProduct) {
        try {
            final CreateProductUseCase.Output output =
                    createProductUseCase.execute(
                            new CreateProductUseCase.Input(
                                    newProduct.getName(),
                                    newProduct.getQuantity(),
                                    newProduct.getPrice(),
                                    newProduct.getDescription(),
                                    newProduct.isActive()));
            return ResponseEntity.created(URI.create("/v1/products/" + output.getId())).body(output);
        } catch (ValidationException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return getProductByIdUseCase.execute(new GetProductByIdUseCase.Input(id))
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deleteProductByIdUseCase.execute(new DeleteProductByIdUseCase.Input(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UpdateProduct updateProduct) {
        try {
            final UpdateProductUseCase.Output output =
                    updateProductUseCase.execute(
                            new UpdateProductUseCase.Input(
                                    id,
                                    updateProduct.getName(),
                                    updateProduct.getQuantity(),
                                    updateProduct.getPrice(),
                                    updateProduct.getDescription(),
                                    updateProduct.isActive()));
            return ResponseEntity.ok().body(output);
        } catch (ValidationException ex) {
            return ResponseEntity.unprocessableEntity().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<GetAllProductUseCase.Output>> getAll() {
        List<GetAllProductUseCase.Output> response = getAllProductUseCase.execute();
        return ResponseEntity.ok().body(response);
    }
}
