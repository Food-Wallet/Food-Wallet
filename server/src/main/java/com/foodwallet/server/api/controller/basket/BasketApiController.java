package com.foodwallet.server.api.controller.basket;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.basket.request.BasketCreateRequest;
import com.foodwallet.server.api.controller.basket.request.BasketModifyRequest;
import com.foodwallet.server.api.service.basket.response.BasketCreateResponse;
import com.foodwallet.server.api.service.basket.response.BasketModifyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{basketMenuId}")
    public ApiResponse<BasketModifyResponse> modifyBasket(
        @PathVariable Long basketMenuId,
        @Valid @RequestBody BasketModifyRequest request
    ) {
        BasketModifyResponse response = BasketModifyResponse.builder()
            .menuName("간장닭강정")
            .count(3)
            .totalPrice(24000)
            .build();
        return ApiResponse.ok(response);
    }
}
