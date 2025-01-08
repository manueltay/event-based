package org.gma.prueba.repository;

import org.gma.prueba.sales.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
