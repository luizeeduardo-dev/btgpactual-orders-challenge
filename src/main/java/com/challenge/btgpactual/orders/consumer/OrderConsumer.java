package com.challenge.btgpactual.orders.consumer;

import com.challenge.btgpactual.orders.entity.Order;
import com.challenge.btgpactual.orders.entity.Status;
import com.challenge.btgpactual.orders.repository.OrderRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderConsumer {

  private final OrderRepository repository;

  public OrderConsumer(OrderRepository repository) {
    this.repository = repository;
  }

  @RabbitListener(queues = "orders-queue")
  public void processOrder(String orderId) {
    log.info("Pedido recebido para processamento: {}", orderId);

    Optional<Order> orderById = this.repository.findById(orderId);

    orderById.ifPresentOrElse(
        order -> {
          try {
            Thread.sleep(2000);

            order.setStatus(Status.PROCESSADO);
            this.repository.save(order);

            log.info("Pedido {} processado e status atualizado.", order.getId());
          } catch (InterruptedException e) {
            log.error("A thread foi interrompida durante o processamento do pedido {}", orderId, e);
            Thread.currentThread().interrupt();
          } catch (Exception e) {
            log.error("Erro ao processar o pedido {}", orderId, e);
            throw e;
          }
        },
        () -> {
          log.warn(
              "Pedido com ID {} não encontrado no repositório. A mensagem será descartada.",
              orderId);
        });
  }
}
