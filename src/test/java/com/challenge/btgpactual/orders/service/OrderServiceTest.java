package com.challenge.btgpactual.orders.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.challenge.btgpactual.orders.entity.Order;
import com.challenge.btgpactual.orders.exception.ResourceNotFoundException;
import com.challenge.btgpactual.orders.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock private AmqpTemplate template;

  @Mock private OrderRepository repository;

  @InjectMocks private OrderService service;

  private static final String QUEUE_NAME = "orders-queue";
  public static final String ID = "OrderId-123";

  @Test
  void createOrderShouldSaveOrderAndSendMessage() {
    Order expectedOrder = orderMock();
    expectedOrder.setId(ID);

    Mockito.doAnswer(
            invocationOnMock -> {
              Order order = invocationOnMock.getArgument(0);
              order.setId(ID);
              return order;
            })
        .when(this.repository)
        .save(any(Order.class));

    Order result = this.service.createOrder(expectedOrder.getClientId(), expectedOrder.getItems());

    assertNotNull(result);
    assertEquals(expectedOrder.getId(), result.getId());
    assertEquals(expectedOrder.getClientId(), result.getClientId());
    assertEquals(expectedOrder.getItems(), result.getItems());

    verify(this.repository, times(1)).save(any(Order.class));
    verify(this.template, times(1)).convertAndSend(QUEUE_NAME, ID);
  }

  @Test
  void getOrderByIdShouldReturnOrderWhenFound() {
    Order order = orderMock();
    order.setId(ID);

    when(this.repository.findById(ID)).thenReturn(Optional.of(order));

    Order result = this.service.getOrderById(ID);

    assertEquals(ID, result.getId());
    verify(this.repository, times(1)).findById(ID);
  }

  @Test
  void getOrderByIdShouldReturnOrderNotFound() {
    String id = "id-nao-existe";
    when(this.repository.findById(id)).thenReturn(Optional.empty());

    ResourceNotFoundException exception =
        assertThrows(
            ResourceNotFoundException.class,
            () -> {
              this.service.getOrderById(id);
            });

    String expectedMessage = "Pedido com ID " + id + " não encontrado.";
    assertEquals(expectedMessage, exception.getMessage());
  }

  private static Order orderMock() {
    String clientId = "client-123";
    List<String> items = List.of("item1", "item2", "item3");

    return new Order(clientId, items);
  }
}
