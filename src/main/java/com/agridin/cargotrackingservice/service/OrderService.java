package com.agridin.cargotrackingservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agridin.cargotrackingservice.model.Order;
import com.agridin.cargotrackingservice.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    
    private OrderRepository repository;

    public Order save(Order order){
        return repository.save(order);
    }

    public List<Order> getAll(){
        return repository.findAll();
    }

    public void setState(Order order, String state){
        order.setState(state);
        save(order);
    }
}
