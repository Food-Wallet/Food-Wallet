package com.foodwallet.server.api.service.order.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderDetailResponse {

    private final Long orderId;
    private final String storeName;
    private final String orderStatus;
    private final int totalPrice;
    private final LocalDateTime orderDateTime;
    private final List<OrderMenuResponse> orderMenus;

    @Builder
    private OrderDetailResponse(Long orderId, String storeName, String orderStatus, int totalPrice, LocalDateTime orderDateTime, List<OrderMenuResponse> orderMenus) {
        this.orderId = orderId;
        this.storeName = storeName;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderDateTime = orderDateTime;
        this.orderMenus = orderMenus;
    }
}
