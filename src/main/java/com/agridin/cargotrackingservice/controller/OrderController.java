package com.agridin.cargotrackingservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agridin.cargotrackingservice.model.Order;
import com.agridin.cargotrackingservice.service.OrderService;

import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("order")
public class OrderController {

    private OrderService orderService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrder(){
        return new ResponseEntity<List<Order>>(orderService.getAll(),HttpStatus.OK);
    }
    
}
