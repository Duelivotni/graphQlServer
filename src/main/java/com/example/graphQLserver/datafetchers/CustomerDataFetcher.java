package com.example.graphQLserver.datafetchers;

import java.util.List;

import com.example.graphQLserver.datastore.CustomerInput;
import com.example.graphQLserver.datastore.CustomerService;
import com.example.graphQLserver.domain.Customer;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class CustomerDataFetcher {

    private final CustomerService customerService;

    @Autowired
    public CustomerDataFetcher(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @DgsQuery
    public List<Customer> getAllCustomers() {
        return customerService.getCustomers();
    }

    @DgsQuery
    public List<Customer> getCustomersWithOrder(@InputArgument String orderName) {
        return customerService.getCustomers(orderName);
    }

    @DgsData(parentType = "Mutation")
    public Customer addCustomer(@InputArgument("input") CustomerInput customerInput) {
        return customerService.addNewCustomer(customerInput);    
    }

    @DgsData(parentType = "Mutation")
    public List<Customer> removeCustomer(@InputArgument("customerName") String customerName) {
        return customerService.deleteCustomer(customerName);
    }
}
