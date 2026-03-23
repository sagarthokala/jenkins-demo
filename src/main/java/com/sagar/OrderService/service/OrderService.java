package com.sagar.OrderService.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderService {

    private final WebClient.Builder webClientBuilder;

    public OrderService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    /*@CircuitBreaker(name = "paymentService", fallbackMethod = "fallbackPayment")
    public String placeOrder(String orderId){

        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/api/payments/"+orderId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String fallbackPayment(String orderId, Throwable t){

        System.out.println("Fallback called for orderId = " + orderId);
        System.out.println("Reason = " + t.getMessage());

        return "Fallback: Payment service is currently unavailable.";
    }*/

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public String placeOrder(String id) {

        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/api/payments/" + id)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorMap(ex -> ex)
                .block();   // <-- this throws WebClientResponseException
    }

    // ✅ fallback must match parameters + exception as LAST argument
    public String paymentFallback(String id, Throwable ex) {

        System.out.println("Fallback triggered because: " + ex.getClass());

        return "Payment service is down. Order placed in PENDING state.";
    }

    //testing webhook 2
}
