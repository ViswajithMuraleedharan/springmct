package com.example.orderApi.dao;

import com.example.orderApi.model.ordertable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderRepository extends JpaRepository<ordertable,Integer> {

}
