package com.todolist.testingjson.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.todolist.testingjson.model.OrderEntity;
import com.todolist.testingjson.repo.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderEntity saveOrder(String jsonPayload) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonPayload);
            JsonNode items = root.get("items");

            List<String> names = new ArrayList<>();

            for (JsonNode item : items) {
                names.add(item.get("name").asText());
            }

            String result = mapper.writeValueAsString(names);

            OrderEntity order = new OrderEntity();
            order.setOrderData(result);

            return orderRepository.save(order);

        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON Payload");
        }
    }
}