package org.gma.prueba.model;

import java.util.List;

public record OrderDTO(String id, CustomerDTO customer, List<ProductDTO> product) {}
