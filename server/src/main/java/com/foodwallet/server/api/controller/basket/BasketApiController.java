package com.foodwallet.server.api.controller.basket;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.basket.request.BasketCreateRequest;
import com.foodwallet.server.api.service.basket.response.BasketCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/baskets")
public class BasketApiController {

    @PostMapping
    public ApiResponse<BasketCreateResponse> createBasket(@Valid @RequestBody BasketCreateRequest request) {
        BasketCreateResponse response = BasketCreateResponse.builder()
            .menuName("간장닭강정")
            .count(2)
            .totalPrice(16000)
            .build();
        return ApiResponse.ok(response);
    }
}
