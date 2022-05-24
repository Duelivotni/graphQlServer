package com.example.graphQLserver.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.leangen.graphql.annotations.GraphQLQuery;

@Entity
public class Customer {
    
    @Id
    @GeneratedValue
    @GraphQLQuery(name = "id")
    private UUID id;

    @GraphQLQuery(name = "fullName")
    private String fullName;

    @GraphQLQuery(name = "orders")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Order> orders;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
