package com.challenge.btgpactual.orders.service;

import com.challenge.btgpactual.orders.entity.Order;
import com.challenge.btgpactual.orders.exception.ResourceNotFoundException;
import com.challenge.btgpactual.orders.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

  private final AmqpTemplate template;
  private final OrderRepository repository;

  public OrderService(AmqpTemplate template, OrderRepository repository) {
    this.template = template;
    this.repository = repository;
  }

  private static final String QUEUE_NAME = "orders-queue";

  public Order createOrder(String clientId, List<String> items) {
    Order order = new Order(clientId, items);
    this.repository.save(order);

    this.template.convertAndSend(QUEUE_NAME, order.getId());

    return order;
  }

  public Order getOrderById(String id) {
    Optional<Order> order = this.repository.findById(id);

    if (order.isEmpty()) {
      throw new ResourceNotFoundException("Pedido com ID " + id + " não encontrado.");
    }

    return order.get();
  }
}
