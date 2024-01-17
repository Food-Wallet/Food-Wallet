package com.foodwallet.server.api.controller.order;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.service.order.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderApiController {

    @PostMapping("/baskets/{basketId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
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
        return ApiResponse.created(response);
    }

    @GetMapping("/orders")
    public ApiResponse<SliceResponse<OrderResponse>> searchOrders(
        @RequestParam Integer page
    ) {
        OrderResponse response = OrderResponse.builder()
            .orderId(1L)
            .storeName("나리닭강정")
            .orderStatus("COMPLETED")
            .totalPrice(25000)
            .orderDateTime(LocalDateTime.of(2024, 1, 17, 18, 0))
            .build();
        PageRequest pageable = PageRequest.of(0, 10);
        SliceImpl<OrderResponse> slice = new SliceImpl<>(List.of(response), pageable, false);

        SliceResponse<OrderResponse> data = SliceResponse.of(slice);
        return ApiResponse.ok(data);
    }

    @GetMapping("/orders/{orderId}")
    public ApiResponse<OrderDetailResponse> searchOrder(@PathVariable Long orderId) {
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
        OrderDetailResponse response = OrderDetailResponse.builder()
            .orderId(1L)
            .storeName("나리닭강정")
            .orderStatus("COMPLETED")
            .totalPrice(25000)
            .orderDateTime(LocalDateTime.of(2024, 1, 17, 18, 0))
            .orderMenus(List.of(menu1, menu2))
            .build();
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/orders/{orderId}")
    public ApiResponse<OrderRemoveResponse> removeOrder(@PathVariable Long orderId) {
        OrderRemoveResponse response = OrderRemoveResponse.builder()
            .orderId(1L)
            .storeName("나리닭강정")
            .orderStatus("COMPLETED")
            .totalPrice(25000)
            .orderDateTime(LocalDateTime.of(2024, 1, 17, 18, 0))
            .build();
        return ApiResponse.ok(response);
    }
}
