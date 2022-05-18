package com.example.grapghQLserver.domain;

import java.util.List;
import java.util.UUID;

public class Customer {
    
    private final UUID id;
    private final String fullName;
    private final List<Order> orders;
    
    public Customer(String fullName, List<Order> orders) {
        this.id = UUID.randomUUID();
        this.fullName = fullName;
        this.orders = orders;
    }
    public UUID getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public List<Order> getOrders() {
        return orders;
    }
}
