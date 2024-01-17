package com.foodwallet.server.api.controller.basket;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.basket.request.BasketCreateRequest;
import com.foodwallet.server.api.controller.basket.request.BasketModifyRequest;
import com.foodwallet.server.api.service.basket.response.BasketCreateResponse;
import com.foodwallet.server.api.service.basket.response.BasketMenuResponse;
import com.foodwallet.server.api.service.basket.response.BasketModifyResponse;
import com.foodwallet.server.api.service.basket.response.BasketResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/baskets")
public class BasketApiController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BasketCreateResponse> createBasket(@Valid @RequestBody BasketCreateRequest request) {
        BasketCreateResponse response = BasketCreateResponse.builder()
            .menuName("간장닭강정")
            .count(2)
            .totalPrice(16000)
            .build();
        return ApiResponse.created(response);
    }

    @GetMapping
    public ApiResponse<BasketResponse> searchBasket() {
        BasketMenuResponse menu1 = BasketMenuResponse.builder()
            .menuId(1L)
            .menuName("간장닭강정")
            .menuImage("s3-store-menu-img-url")
            .count(2)
            .totalPrice(16000)
            .build();
        BasketMenuResponse menu2 = BasketMenuResponse.builder()
            .menuId(2L)
            .menuName("양념닭강정")
            .menuImage("s3-store-menu-img-url")
            .count(1)
            .totalPrice(9000)
            .build();
        BasketResponse response = BasketResponse.builder()
            .basketId(1L)
            .storeName("나리닭강정")
            .totalPrice(25000)
            .menus(List.of(menu1, menu2))
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
