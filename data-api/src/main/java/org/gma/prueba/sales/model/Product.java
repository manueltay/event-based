package org.gma.prueba.sales.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public record Product(@Id String id, String name, int price, String description) {

}