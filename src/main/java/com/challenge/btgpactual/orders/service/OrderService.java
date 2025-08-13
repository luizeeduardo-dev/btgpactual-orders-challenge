package com.challenge.btgpactual.orders.service;

import com.challenge.btgpactual.orders.entity.Order;
import com.challenge.btgpactual.orders.repository.OrderRepository;
import java.util.List;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  @Autowired private OrderRepository repository;

  public Order createOrder(String clientId, List<String> items) {
    Order order = new Order(clientId, items);
    this.repository.save(order);

    return order;
  }

  public Order getOrderById(String id) {
    return this.repository.getOrderById(id);
  }
}
