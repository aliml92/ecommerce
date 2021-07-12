package com.xco.spactshop.controller;


import com.xco.spactshop.model.Order;
import com.xco.spactshop.repository.OrderRepository;
import com.xco.spactshop.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {


    private final OrderRepository orderRepo;
    private final UserRepository userRepo;

    public OrderController(OrderRepository orderRepo, UserRepository userRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @PostMapping
    public ResponseEntity<?> addOrderItems(Authentication auth,
                                           @RequestBody Order orderReq){
        String id = userRepo.findByEmail(auth.getName()).get().get_id();
        orderReq.setUser(id);
        Order savedOrder =  orderRepo.save(orderReq);
        return ResponseEntity.status(201).body(savedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> addOrderItems(@PathVariable String id){
        Order order = orderRepo.findById(id).get();
        return ResponseEntity.status(201).body(order);
    }

    @GetMapping("/myorders")
    public ResponseEntity<?> getOrders(Authentication auth){
        String user_id = userRepo.findByEmail(auth.getName()).get().get_id();
        List<Order> myorders = orderRepo.findAllByUser(user_id);
        return ResponseEntity.ok(myorders);
    }
}
