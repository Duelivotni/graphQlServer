package com.example.grapghQLserver.domain;

import java.util.UUID;

public class Order {
    
    private final UUID id;
    private final String name;
    private final Double price;

    public Order(String name, Double price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
    }
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Double getPrice() {
        return price;
    }
}
