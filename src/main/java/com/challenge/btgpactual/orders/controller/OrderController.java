package com.challenge.btgpactual.orders.controller;

import com.challenge.btgpactual.orders.dto.OrderRequest;
import com.challenge.btgpactual.orders.dto.OrderResponse;
import com.challenge.btgpactual.orders.entity.Order;
import com.challenge.btgpactual.orders.service.OrderService;
import jakarta.validation.Valid;
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

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest payload) {

    Order order = this.orderService.createOrder(payload.clientId(), payload.items());
    OrderResponse orderResponse = OrderResponse.fromEntity(order);

    return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getOrder(@PathVariable String id) {
    Order order = this.orderService.getOrderById(id);

    OrderResponse orderResponse = OrderResponse.fromEntity(order);

    return ResponseEntity.ok(orderResponse);
  }
}
