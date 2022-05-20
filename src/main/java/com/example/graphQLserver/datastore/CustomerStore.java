package com.example.graphQLserver.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.graphQLserver.domain.Customer;
import com.example.graphQLserver.domain.Order;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CustomerStore {

    private List<Customer> customerOrderData = new ArrayList<>(List.of(
        new Customer("John", List.of(
            new Order("Potatoe", 15.55), new Order("Cucumber", 16.20))),
        new Customer("Matt", List.of(
            new Order("Tomato", 12.60), new Order("Carrot", 16.20)))
    ));
    
    /** 
     * Получение списка всех клиентов
     */
    public List<Customer> getCustomers() {
        return getCustomers(null);
    }
    
    /** 
     * Поиск клиентов по наличию у них определенного товара в заказах
     * 
     * @param String orderName - наименование товара
     * @return List<Customer> - список клиентов
     */
    public List<Customer> getCustomers(@Nullable String orderName) {
        if (orderName == null) return customerOrderData;
        return customerOrderData
        .parallelStream()
        .filter(
            customer -> customer.getOrders()
            .parallelStream()
            .anyMatch(order -> order.getName().equals(orderName)))
        .collect(Collectors.toList());
    }

    /** 
     * Добавить нового клиента в список
     * 
     * @param customerInput - вводные данные о клиенте и его заказах
     * @return клиент
     */
    public Customer addNewCustomer(CustomerInput customerInput) {
        String fullName = customerInput.getFullName();
        List<Order> orders = customerInput.getOrders()
        .stream()
        .map(order -> new Order(order.getName(), order.getPrice()))
        .collect(Collectors.toList());
        Customer customer = new Customer(fullName, orders);
        customerOrderData.add(customer);    
        return customer;
    }
}
