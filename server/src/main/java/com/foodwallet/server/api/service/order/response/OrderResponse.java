package com.foodwallet.server.api.service.order.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponse {

    private final Long orderId;
    private final String storeName;
    private final String orderStatus;
    private final int totalPrice;
    private final LocalDateTime orderDateTime;

    @Builder
    private OrderResponse(Long orderId, String storeName, String orderStatus, int totalPrice, LocalDateTime orderDateTime) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderDateTime = orderDateTime;
    }
}
