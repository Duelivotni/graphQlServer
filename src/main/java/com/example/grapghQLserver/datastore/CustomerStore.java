package com.example.grapghQLserver.datastore;

import java.util.List;
import java.util.stream.Collectors;

import com.example.grapghQLserver.domain.Customer;
import com.example.grapghQLserver.domain.Order;

import org.springframework.lang.Nullable;

public class CustomerStore {

    private static final List<Customer> cutomerOrderData = List.of(
        new Customer("John", List.of(
            new Order("Potatoe", 15.55), new Order("Cucumber", 16.20))),
        new Customer("Matt", List.of(
            new Order("Tomato", 12.60), new Order("Carrot", 16.20)))
    );
    
    /** 
     * Получение списка всех клиентов
     */
    public static List<Customer> getCustomers() {
        return getCustomers(null);
    }
    
    /** 
     * Поиск клиентов по наличию у них определенного товара в заказе
     * 
     * @param String orderName - наименование товара
     * @return List<Customer> - список клиентов
     */
    public static List<Customer> getCustomers(@Nullable String orderName) {
        if (orderName == null) return cutomerOrderData;
        return cutomerOrderData
        .stream()
        .filter(
            customer -> customer.getOrders()
            .stream()
            .anyMatch(order -> order.getName() == orderName))
        .collect(Collectors.toList());
    }
}
