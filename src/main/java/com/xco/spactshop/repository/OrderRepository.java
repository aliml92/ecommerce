package com.xco.spactshop.repository;

import com.xco.spactshop.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order,String> {

    List<Order> findAllByUser(String user_id);
}
