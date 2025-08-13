package com.challenge.btgpactual.orders.repository;

import com.challenge.btgpactual.orders.entity.Order;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class OrderRepository {

  private final ConcurrentHashMap<String, Order> orders = new ConcurrentHashMap<>();

  public void save(Order order) {
    this.orders.put(order.getId(), order);
  }

  public Order getOrderById(String id) {
    return this.orders.get(id);
  }
}
