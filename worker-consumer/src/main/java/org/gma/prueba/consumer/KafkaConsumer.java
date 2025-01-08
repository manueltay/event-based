package org.gma.prueba.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.gma.prueba.model.OrderDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//Cada mensaje contendrá un identificador único de pedido, el ID del cliente y una lista de productos.
@Service
public class KafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "test-topic")
    public void consume(String message) {
        try {
            System.out.println("Received message: " + message);
            OrderDTO order = objectMapper.readValue(message, OrderDTO.class);
            processOrder(order);
            System.out.println("Parsed OrderDTO: " + order);
        } catch (Exception e) {
            System.err.println("Error parsing message: " + e.getMessage());
        }
    }

    private void processOrder(OrderDTO order) {

    }
}