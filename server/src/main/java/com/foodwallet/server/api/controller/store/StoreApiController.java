package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import com.foodwallet.server.api.controller.store.request.StoreOpenRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.api.service.store.response.StoreOpenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreApiController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<StoreCreateResponse> createStore(@Valid @RequestBody StoreCreateRequest request) {
        StoreCreateResponse response = StoreCreateResponse.builder()
            .type("치킨")
            .name("나리닭강정")
            .description(null)
            .createdDateTime(LocalDateTime.of(2024, 1, 16, 18, 0))
            .build();
        return ApiResponse.created(response);
    }

    @PatchMapping("/{storeId}/open")
    public ApiResponse<StoreOpenResponse> openStore(@Valid @RequestBody StoreOpenRequest request, @PathVariable Long storeId) {
        StoreOpenResponse response = StoreOpenResponse.builder()
            .name("나리닭강정")
            .address("서울 중구 세종대로 110")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .openDateTime(LocalDateTime.of(2023, 1, 16, 14, 0))
            .build();
        return ApiResponse.ok(response);
    }

}
