package com.challenge.btgpactual.orders.entity;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  private String id;
  private String clientId;
  private List<String> items;
  private Status status;

  public Order(String clientId, List<String> items) {
    this.id = UUID.randomUUID().toString();
    this.clientId = clientId;
    this.items = items;
    this.status = Status.PENDENTE;
  }
}
