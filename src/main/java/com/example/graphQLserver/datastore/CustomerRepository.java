package com.example.graphQLserver.datastore;

import java.util.List;
import java.util.UUID;

import com.example.graphQLserver.domain.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query("select c from Customer c left join c.orders o where o.name = :orderName")
    public List<Customer> findAllWithOrder(@Param("orderName") String orderName);

    @Transactional
    @Modifying
    @Query("delete from Customer c where c.fullName = :customerName")
    public void deleteByName(@Param("customerName") String customerName);
}
