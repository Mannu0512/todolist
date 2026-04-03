package com.todolist.testingjson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.todolist.testingjson.model.OrderEntity;
import com.todolist.testingjson.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderEntity createOrder(@RequestBody String jsonPayload) {

        return orderService.saveOrder(jsonPayload);
    }


}