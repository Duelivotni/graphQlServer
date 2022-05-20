package com.example.graphQLserver.datastore;

import java.util.List;

public class CustomerInput {
      
    private String fullName;
    private List<OrderInput> orders;

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public List<OrderInput> getOrders() {
        return orders;
    }
    public void setOrders(List<OrderInput> orders) {
        this.orders = orders;
    }
}
