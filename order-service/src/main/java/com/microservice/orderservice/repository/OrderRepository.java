package com.microserivce.eurekaserver.orderservice.repository;

import com.microserivce.eurekaserver.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {


}
