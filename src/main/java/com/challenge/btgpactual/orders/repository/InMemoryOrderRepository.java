package com.challenge.btgpactual.orders.repository;

import com.challenge.btgpactual.orders.entity.Order;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

  private final Map<String, Order> orders = new ConcurrentHashMap<>();

  @Override
  public void save(Order order) {
    this.orders.put(order.getId(), order);
  }

  @Override
  public Optional<Order> findById(String id) {
    return Optional.ofNullable(this.orders.get(id));
  }
}
