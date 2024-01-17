package com.foodwallet.server.api.controller.order;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.service.order.response.OrderCreateResponse;
import com.foodwallet.server.api.service.order.response.OrderMenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/baskets/{basketId}/orders")
public class OrderApiController {

    @PostMapping
    public ApiResponse<OrderCreateResponse> createOrder(@PathVariable Long basketId) {
        OrderMenuResponse menu1 = OrderMenuResponse.builder()
            .menuName("간장닭강정")
            .menuImage("s3-store-menu-img-url")
            .orderPrice(16000)
            .count(2)
            .build();
        OrderMenuResponse menu2 = OrderMenuResponse.builder()
            .menuName("양념닭강정")
            .menuImage("s3-store-menu-img-url")
            .orderPrice(9000)
            .count(1)
            .build();
        OrderCreateResponse response = OrderCreateResponse.builder()
            .orderId(1L)
            .totalPrice(25000)
            .orderStatus("INIT")
            .orderDateTime(LocalDateTime.of(2024, 1, 17, 18, 0))
            .orderMenus(List.of(menu1, menu2))
            .build();
        return ApiResponse.ok(response);
    }
}
