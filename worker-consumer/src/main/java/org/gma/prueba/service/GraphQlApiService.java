package org.gma.prueba.service;

import lombok.RequiredArgsConstructor;
import org.gma.prueba.model.CustomerDTO;
import org.gma.prueba.model.OrderDTO;
import org.gma.prueba.model.ProductDTO;
import org.gma.prueba.model.PurchaseDTO;
import org.gma.prueba.request.GraphQLRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GraphQlApiService {
    private final RestTemplate restTemplate;

    private static final String GRAPHQL_ENDPOINT = "http://localhost:8080/graphql";


    public ProductDTO getProductById(String id) {
        String query = """
            query($id: ID) {
                productById(id: $id) {
                    id
                    name
                    description
                    price
                }
            }
        """;

        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);

        GraphQLRequest request = new GraphQLRequest(query, variables);
        ResponseEntity<Map> response = sendRequest(request);

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        Map<String, Object> product = (Map<String, Object>) data.get("productById");

        if (product != null)
            return new ProductDTO(
                    (String) product.get("id"),
                    (String) product.get("name"),
                    (String) product.get("description"),
                    (Integer) product.get("price")
            );
        else
            throw new RuntimeException("Entity does not exists");
    }

    public CustomerDTO getCustomerById(String id) {
        String query = """
            query($id: ID) {
                customerById(id: $id) {
                    id
                    name
                    address
                }
            }
        """;

        Map<String, Object> variables = new HashMap<>();
        variables.put("id", id);

        GraphQLRequest request = new GraphQLRequest(query, variables);
        ResponseEntity<Map> response = sendRequest(request);

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        Map<String, Object> customer = (Map<String, Object>) data.get("customerById");

        if (customer != null) {
            return new CustomerDTO(
                    (String) customer.get("id"),
                    (String) customer.get("name"),
                    (String) customer.get("address")
            );
        } else
            throw new RuntimeException("Entity does not exists");
    }


    public ProductDTO addProduct(String name, String description, int price) {
        String mutation = """
            mutation($name: String, $description: String, $price: Int) {
                addProduct(name: $name, description: $description, price: $price) {
                    id
                    name
                    description
                    price
                }
            }
        """;

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", name);
        variables.put("description", description);
        variables.put("price", price);

        GraphQLRequest request = new GraphQLRequest(mutation, variables);
        ResponseEntity<Map> response = sendRequest(request);

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        Map<String, Object> product = (Map<String, Object>) data.get("addProduct");

        return new ProductDTO(
                (String) product.get("id"),
                (String) product.get("name"),
                (String) product.get("description"),
                (Integer) product.get("price")
        );
    }

    public CustomerDTO addCustomer(String name, String address) {
        String mutation = """
            mutation($name: String, $address: String) {
                addCustomer(name: $name, address: $address) {
                    id
                    name
                    address
                }
            }
        """;

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", name);
        variables.put("address", address);

        GraphQLRequest request = new GraphQLRequest(mutation, variables);
        ResponseEntity<Map> response = sendRequest(request);

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        Map<String, Object> customer = (Map<String, Object>) data.get("addCustomer");

        return new CustomerDTO(
                (String) customer.get("id"),
                (String) customer.get("name"),
                (String) customer.get("address")
        );
    }

    public PurchaseDTO addPurchase(String customerId, String productId, int quantity) {
        String mutation = """
            mutation($customerId: ID, $productId: ID, $quantity: Int) {
                addPurchase(customerId: $customerId, productId: $productId, quantity: $quantity) {
                    id
                    customer {
                        id
                        name
                        address
                    }
                    product {
                        id
                        name
                        description
                        price
                    }
                    quantity
                    date
                }
            }
        """;

        Map<String, Object> variables = new HashMap<>();
        variables.put("customerId", customerId);
        variables.put("productId", productId);
        variables.put("quantity", quantity);

        GraphQLRequest request = new GraphQLRequest(mutation, variables);
        ResponseEntity<Map> response = sendRequest(request);

        Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
        Map<String, Object> purchase = (Map<String, Object>) data.get("addPurchase");

        Map<String, Object> customer = (Map<String, Object>) purchase.get("customer");
        Map<String, Object> product = (Map<String, Object>) purchase.get("product");

        return new PurchaseDTO(
                (String) purchase.get("id"),
                new CustomerDTO(
                        (String) customer.get("id"),
                        (String) customer.get("name"),
                        (String) customer.get("address")
                ),
                new ProductDTO(
                        (String) product.get("id"),
                        (String) product.get("name"),
                        (String) product.get("description"),
                        (Integer) product.get("price")
                ),
                (Integer) purchase.get("quantity"),
                (String) purchase.get("date")
        );
    }

    private ResponseEntity<Map> sendRequest(GraphQLRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<GraphQLRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForEntity(GRAPHQL_ENDPOINT, entity, Map.class);
    }

    public void addPurchaseOrder(OrderDTO order) {
        for (ProductDTO productDTO : order.product()) {
            addPurchase(order.customer().id(), productDTO.id(), 1);
        }
    }
}

