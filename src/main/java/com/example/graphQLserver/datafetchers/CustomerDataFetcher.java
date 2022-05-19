package com.example.graphQLserver.datafetchers;

import java.util.List;

import com.example.graphQLserver.datastore.CustomerStore;
import com.example.graphQLserver.domain.Customer;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class CustomerDataFetcher {

    @DgsQuery
    public List<Customer> getAllCustomers() {
        return CustomerStore.getCustomers();
    }

    @DgsQuery
    public List<Customer> getCustomersWithOrder(@InputArgument String orderName) {
        return CustomerStore.getCustomers(orderName);
    }
}
