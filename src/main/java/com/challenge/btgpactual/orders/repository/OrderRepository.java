package com.challenge.btgpactual.orders.repository;

import com.challenge.btgpactual.orders.entity.Order;
import java.util.Optional;

public interface OrderRepository {

  void save(Order order);

  Optional<Order> findById(String id);
}
