package com.challenge.btgpactual.orders.controller;

import com.challenge.btgpactual.orders.entity.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @PostMapping
  public Order createOrder() {

    return null;
  }

  @GetMapping("/{id}")
  public Order getOrder(@PathVariable String id) {
    return null;
  }
}
