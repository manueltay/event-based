package org.gma.prueba.client;

import org.gma.prueba.request.GraphQLRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ClientService {
    private final RestTemplate restTemplate;

    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String executeQuery(String endpoint, String query, Map<String, Object> variables) {
        GraphQLRequest graphQLRequest = new GraphQLRequest(query, variables);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<GraphQLRequest> request = new HttpEntity<>(graphQLRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.POST, request, String.class);

        return response.getBody();
    }
}

