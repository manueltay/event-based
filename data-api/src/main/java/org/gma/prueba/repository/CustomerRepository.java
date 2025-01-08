package org.gma.prueba.repository;

import org.gma.prueba.sales.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}

