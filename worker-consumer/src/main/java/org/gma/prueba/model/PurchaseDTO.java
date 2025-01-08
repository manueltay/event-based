package org.gma.prueba.model;

public record PurchaseDTO(String id, CustomerDTO customer, ProductDTO productId, int quantity, String date) {

}
