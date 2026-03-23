package com.sagar.OrderService.controller;

import com.sagar.OrderService.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<String> createOrder(@PathVariable String orderId){
        return ResponseEntity.ok(orderService.placeOrder(orderId));
    }



    @GetMapping("/test")
    public String test(){
        return "Hello from Orders!";
    }

}
