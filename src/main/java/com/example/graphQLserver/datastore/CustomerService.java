package com.example.graphQLserver.datastore;

import java.util.List;
import java.util.stream.Collectors;

import com.example.graphQLserver.domain.Customer;
import com.example.graphQLserver.domain.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

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
        return orderName == null ? 
        (List<Customer>) customerRepository.findAll() : customerRepository.findAllWithOrder(orderName);
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
        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setOrders(orders);
        return customerRepository.save(customer);
    }

    /** 
     * Удаление клиента по его имени
     * 
     * @param customerName - имя клиента
     * @return List<Customer> - список всех клиентов за вычетом удаленного
     */
    public List<Customer> deleteCustomer(String customerName) {
        customerRepository.deleteByName(customerName);
        return getCustomers();
        //throw new DgsEntityNotFoundException("Customer is not found");
    }
}
