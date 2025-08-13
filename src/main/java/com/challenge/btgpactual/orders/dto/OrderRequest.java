package com.challenge.btgpactual.orders.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record OrderRequest(
    @NotBlank(message = "O 'clientId' não pode ser nulo ou vazio.") String clientId,
    @NotEmpty(message = "A lista de 'itens' não pode ser vazia.") List<String> items) {}
