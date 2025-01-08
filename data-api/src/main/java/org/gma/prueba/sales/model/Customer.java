package org.gma.prueba.sales.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customers")
public record Customer(@Id String id, String name, String address, boolean active) {


}