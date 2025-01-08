package org.gma.prueba.controller;

import lombok.RequiredArgsConstructor;
import org.gma.prueba.model.CustomerDTO;
import org.gma.prueba.model.ProductDTO;
import org.gma.prueba.service.GraphQlApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "graph")
@RequiredArgsConstructor
public class GraphQLController {

    private final GraphQlApiService apiService;

    @GetMapping("/product")
    public ProductDTO getProductById(@RequestParam String id) {
        return apiService.getProductById(id);
    }

    @GetMapping("/customer")
    public CustomerDTO getCustomerById(@RequestParam String id) {
        return apiService.getCustomerById(id);
    }
}
