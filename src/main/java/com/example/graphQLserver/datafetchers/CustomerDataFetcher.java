package com.example.graphQLserver.datafetchers;

import java.util.List;

import com.example.graphQLserver.datastore.CustomerInput;
import com.example.graphQLserver.datastore.CustomerStore;
import com.example.graphQLserver.domain.Customer;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class CustomerDataFetcher {

    private final CustomerStore customerStore;

    @Autowired
    public CustomerDataFetcher(CustomerStore customerStore) {
        this.customerStore = customerStore;
    }
    
    @DgsQuery
    public List<Customer> getAllCustomers() {
        return customerStore.getCustomers();
    }

    @DgsQuery
    public List<Customer> getCustomersWithOrder(@InputArgument String orderName) {
        return customerStore.getCustomers(orderName);
    }

    @DgsData(parentType = "Mutation")
    public Customer addCustomer(@InputArgument("input") CustomerInput customerInput) {
        return customerStore.addNewCustomer(customerInput);    
    }
}
