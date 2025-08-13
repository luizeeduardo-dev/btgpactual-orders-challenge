package com.challenge.btgpactual.orders.controller;

import com.challenge.btgpactual.orders.entity.Order;
import com.challenge.btgpactual.orders.service.OrderService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @Autowired OrderService orderService;

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> payload) {
    String clientId = (String) payload.get("clientId");
    List<String> items = (List<String>) payload.get("items");

    Order order = this.orderService.createOrder(clientId, items);
    return new ResponseEntity<>(order, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrder(@PathVariable String id) {
    Optional<Order> order = Optional.ofNullable(this.orderService.getOrderById(id));

    return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
