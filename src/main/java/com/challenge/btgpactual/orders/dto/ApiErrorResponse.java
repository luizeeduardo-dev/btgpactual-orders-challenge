package com.challenge.btgpactual.orders.dto;

import java.time.LocalDateTime;

public record ApiErrorResponse(
    LocalDateTime timestamp, int status, String error, String message, String path) {}
