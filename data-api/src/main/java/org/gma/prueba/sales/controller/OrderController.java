package org.gma.prueba.sales.controller;

import lombok.RequiredArgsConstructor;
import org.gma.prueba.exceptions.EntityNotFoundException;
import org.gma.prueba.repository.CustomerRepository;
import org.gma.prueba.repository.ProductRepository;
import org.gma.prueba.repository.PurchaseRepository;
import org.gma.prueba.sales.model.Customer;
import org.gma.prueba.sales.model.Product;
import org.gma.prueba.sales.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final PurchaseRepository purchaseRepository;

    @QueryMapping
    public Product productById(@Argument String id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));
    }

    @QueryMapping
    public Customer customerById(@Argument String id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer with ID " + id + " not found"));
    }

    @QueryMapping
    public List<Purchase> purchaseHistory(@Argument String customerId) {
        return purchaseRepository.findAllByCustomerId(customerId);
    }

    @MutationMapping
    public Customer addCustomer(@Argument String name, @Argument String address, @Argument boolean active) {
        Customer customer = new Customer(null, name, address, active);
        return customerRepository.save(customer);
    }

    @MutationMapping
    public Product addProduct(@Argument String name, @Argument String description, @Argument int price) {
        Product product = new Product(null, name, price, description);
        return productRepository.save(product);
    }

    @MutationMapping
    public Purchase addPurchase(@Argument String customerId, @Argument String productId, @Argument int quantity) {
        Purchase purchase = new Purchase(null, customerId, productId, quantity, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        return purchaseRepository.save(purchase);
    }

    @SchemaMapping
    public Customer customer(Purchase purchase) {
        return customerRepository.findById(purchase.customerId()).orElse(null);
    }

    @SchemaMapping
    public Product product(Purchase purchase) {
        return productRepository.findById(purchase.productId()).orElse(null);
    }

}
