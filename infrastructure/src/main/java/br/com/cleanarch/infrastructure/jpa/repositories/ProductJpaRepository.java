package br.com.cleanarch.infrastructure.jpa.repositories;

import br.com.cleanarch.infrastructure.jpa.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductJpaRepository extends CrudRepository<ProductEntity, UUID>{

}
