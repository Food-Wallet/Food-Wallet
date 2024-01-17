package com.foodwallet.server.api.service.order.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderCreateResponse {

    private final Long orderId;
    private final int totalPrice;
    private final String orderStatus;
    private final LocalDateTime orderDateTime;
    private final List<OrderMenuResponse> orderMenus;

    @Builder
    private OrderCreateResponse(Long orderId, int totalPrice, String orderStatus, LocalDateTime orderDateTime, List<OrderMenuResponse> orderMenus) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderDateTime = orderDateTime;
        this.orderMenus = orderMenus;
    }
}
