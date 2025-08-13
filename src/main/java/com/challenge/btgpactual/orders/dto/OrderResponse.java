package com.challenge.btgpactual.orders.dto;

import com.challenge.btgpactual.orders.entity.Order;
import com.challenge.btgpactual.orders.entity.Status;
import java.util.List;

public record OrderResponse(String id, String clientId, List<String> items, Status status) {

  public static OrderResponse fromEntity(Order order) {
    return new OrderResponse(
        order.getId(), order.getClientId(), order.getItems(), order.getStatus());
  }
}
