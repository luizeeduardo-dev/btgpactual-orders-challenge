package com.challenge.btgpactual.orders.consumer;

import com.challenge.btgpactual.orders.entity.Order;
import com.challenge.btgpactual.orders.entity.Status;
import com.challenge.btgpactual.orders.repository.OrderRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConsumer {

  private final OrderRepository repository;

  public OrderConsumer(OrderRepository repository) {
    this.repository = repository;
  }

  private static final String QUEUE_NAME = "orders-queue";

  @RabbitListener(queues = QUEUE_NAME)
  public void processOrder(String orderId) {
    log.info("pedido recebido para processamento: {}", orderId);

    try {
      Thread.sleep(20000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    Optional<Order> order = this.repository.findById(orderId);
    if (order.isPresent()) {
      Order orderToUpdate = order.get();
      orderToUpdate.setStatus(Status.PROCESSADO);
      this.repository.save(orderToUpdate);
      log.info("Pedido {} processado e status atualizado.", orderToUpdate.getId());
    }
  }
}
