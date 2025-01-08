package org.gma.prueba.repository;

import org.gma.prueba.sales.model.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PurchaseRepository extends MongoRepository<Purchase, String> {

    public List<Purchase> findAllByCustomerId(String customerId);
}
