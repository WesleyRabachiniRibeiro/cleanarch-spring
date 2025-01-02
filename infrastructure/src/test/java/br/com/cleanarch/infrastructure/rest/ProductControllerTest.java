package br.com.cleanarch.infrastructure.rest;

import br.com.cleanarch.application.product.CreateProductUseCase;
import br.com.cleanarch.domain.product.Product;
import br.com.cleanarch.domain.product.ProductId;
import br.com.cleanarch.domain.product.ProductRepository;
import br.com.cleanarch.infrastructure.dtos.NewProduct;
import br.com.cleanarch.infrastructure.dtos.UpdateProduct;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um produto")
    public void testCreate() throws Exception {
        NewProduct product = new NewProduct("Iphone 16", 50, BigDecimal.ONE, "Iphone 16 Pro Max", true);

        String result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(product))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn().getResponse().getContentAsString();

        CreateProductUseCase.Output response = mapper.readValue(result, CreateProductUseCase.Output.class);
        Assertions.assertEquals(product.getName(), response.getName());
        Assertions.assertEquals(product.getQuantity(), response.getQuantity());
        Assertions.assertEquals(product.getDescription(), response.getDescription());
        Assertions.assertEquals(product.getPrice(), response.getPrice());
        Assertions.assertTrue(product.isActive());
    }

    @Test
    @DisplayName("Deve recuperar um produto pelo ID")
    public void testGetProductById() throws Exception {
        NewProduct product = new NewProduct("Iphone 16", 50, BigDecimal.valueOf(1.0), "Iphone 16 Pro Max", true);
        String result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(product))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        CreateProductUseCase.Output response = mapper.readValue(result, CreateProductUseCase.Output.class);
        String productId = response.getId();

        this.mvc.perform(MockMvcRequestBuilders.get("/v1/products/{id}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(product.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(product.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(product.isActive()));
    }

    @Test
    @DisplayName("Deve retornar 404 quando o produto não for encontrado")
    public void testGetProductByIdNotFound() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/v1/products/{id}", "123e4567-e89b-12d3-a456-426614174000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar um produto pelo ID")
    public void testDeleteProduct() throws Exception {
        NewProduct product = new NewProduct("Iphone 16", 50, BigDecimal.ONE, "Iphone 16 Pro Max", true);
        String result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(product))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        CreateProductUseCase.Output response = mapper.readValue(result, CreateProductUseCase.Output.class);

        String productId = response.getId();

        this.mvc.perform(MockMvcRequestBuilders.delete("/v1/products/{id}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        this.mvc.perform(MockMvcRequestBuilders.get("/v1/products/{id}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Should update a product successfully")
    public void testUpdateProduct() throws Exception {
        NewProduct product = new NewProduct(
                "Iphone 16",
                50,
                BigDecimal.ONE,
                "Iphone 16 Pro Max",
                true);

        UpdateProduct updateProduct = new UpdateProduct(
                "Iphone 17",
                70,
                BigDecimal.TEN,
                "Iphone 17 Pro Max",
                true);

        String createdProduct = this.mvc.perform(
                        MockMvcRequestBuilders.post("/v1/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(product))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn().getResponse().getContentAsString();

        CreateProductUseCase.Output response = mapper.readValue(createdProduct, CreateProductUseCase.Output.class);

        String productId = response.getId();

       mvc.perform(MockMvcRequestBuilders.put("/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateProduct)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updateProduct.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(updateProduct.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(updateProduct.getPrice().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(updateProduct.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(updateProduct.isActive()));
    }

    @Test
    @DisplayName("Should return 422 when product to update is unprocessable entity")
    public void testUpdateProductUnprocessableEntity() throws Exception {
        String nonExistentProductId = "123e4567-e89b-12d3-a456-426614174000";

        UpdateProduct updateProduct = new UpdateProduct(
                "Updated Laptop", 10, new BigDecimal("5000.00"), "Updated performance", false);

        this.mvc.perform(
                        MockMvcRequestBuilders.put("/v1/products/{id}", nonExistentProductId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(updateProduct))
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("Should return all products")
    public void testGetAllProducts() throws Exception {
        // Arrange
        Product product1 = new Product(
                ProductId.unique(),
                "Smartphone",
                10,
                new BigDecimal("1500.00"),
                "Latest model",
                true
        );

        Product product2 = new Product(
                ProductId.unique(),
                "Laptop",
                5,
                new BigDecimal("3500.00"),
                "High-performance laptop",
                true
        );

        productRepository.save(product1);
        productRepository.save(product2);

        this.mvc.perform(MockMvcRequestBuilders.get("/v1/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(product1.getProductId().getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(product1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(product2.getProductId().getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(product2.getName()));
    }

    @Test
    @DisplayName("Should return empty list when no products exist")
    public void testGetAllProductsEmpty() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/v1/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }
}
