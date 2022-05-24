package com.example.graphQLserver.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.leangen.graphql.annotations.GraphQLQuery;

@Entity
@Table(name = "_order")
public class Order {
    
    @Id
    @GeneratedValue
    @GraphQLQuery(name = "id")
    private UUID id;

    @GraphQLQuery(name = "name")
    private String name;

    @GraphQLQuery(name = "price")
    private Double price;

    @GraphQLQuery(name = "customer")
    @ManyToOne
    private Customer customer;

    public Order(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Order() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
