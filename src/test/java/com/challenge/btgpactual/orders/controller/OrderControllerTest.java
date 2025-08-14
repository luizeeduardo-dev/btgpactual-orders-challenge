package com.challenge.btgpactual.orders.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.btgpactual.orders.dto.OrderRequest;
import com.challenge.btgpactual.orders.entity.Order;
import com.challenge.btgpactual.orders.entity.Status;
import com.challenge.btgpactual.orders.exception.ResourceNotFoundException;
import com.challenge.btgpactual.orders.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private OrderService orderService;

  @Value("classpath:OrderControllerTest/response_get_order_by_id.json")
  private Resource responseJsonResource;

  @Test
  @DisplayName("POST /orders - Deve retornar 201 Created quando o payload for válido")
  void createOrder_whenPayloadIsValid() throws Exception {

    OrderRequest request = new OrderRequest("client-123", List.of("item-A"));

    Order order = createOrder("id-123");

    when(this.orderService.createOrder(anyString(), any(List.class))).thenReturn(order);

    this.mockMvc
        .perform(
            post("/orders")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(this.objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)));
  }

  @Test
  @DisplayName("GET /orders/{id} - Deve retornar 200 OK e o pedido quando o ID existir")
  void getOrder_whenOrderExists() throws Exception {
    String orderId = "id-123";
    Order order = createOrder(orderId);

    when(this.orderService.getOrderById(orderId)).thenReturn(order);

    String expectedJsonResponse =
        this.responseJsonResource.getContentAsString(StandardCharsets.UTF_8);

    this.mockMvc
        .perform(get("/orders/{id}", orderId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(String.valueOf(MediaType.APPLICATION_JSON)))
        .andExpect(content().json(expectedJsonResponse));
  }

  @Test
  @DisplayName("GET /orders/{id} - Deve retornar 404 Not Found quando o pedido não existir")
  void getOrder_whenOrderDoesNotExist() throws Exception {

    String id = "id-123";
    when(this.orderService.getOrderById(id))
        .thenThrow(new ResourceNotFoundException("Pedido não encontrado"));

    this.mockMvc.perform(get("/orders/{id}", id)).andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("POST /orders - Deve retornar 400 Bad Request quando o payload for inválido")
  void createOrder_whenPayloadIsInvalid() throws Exception {
    OrderRequest invalidRequest = new OrderRequest("", List.of("item-A"));

    this.mockMvc
        .perform(
            post("/orders")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(this.objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }

  private static Order createOrder(String orderId) {
    Order order = new Order("client-123", List.of("item-A"));
    order.setId(orderId);
    order.setStatus(Status.PROCESSADO);
    return order;
  }
}
