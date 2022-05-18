package com.example.grapghQLserver.datafetchers;

import java.util.List;

import com.example.grapghQLserver.datastore.CustomerStore;
import com.example.grapghQLserver.domain.Customer;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

@DgsComponent
public class CutomerDataFetcher {

    @DgsQuery
    public List<Customer> getAllCustomers() {
        return CustomerStore.getCustomers();
    }

    @DgsQuery
    public List<Customer> getCustomersWithOrder(@InputArgument String orderName) {
        return CustomerStore.getCustomers(orderName);
    }
}
