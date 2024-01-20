package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.controller.store.request.StoreSearchRequest;
import com.foodwallet.server.api.service.store.StoreQueryService;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreQueryApiController {

    private final StoreQueryService storeQueryService;

    @GetMapping
    public ApiResponse<SliceResponse<StoreResponse>> searchStores(@Valid StoreSearchRequest request) {
        StoreResponse response = StoreResponse.builder()
            .storeId(1L)
            .type(StoreType.CHICKEN)
            .name("나리닭강정")
            .address("서울 중구 세종대로 110")
            .openTime("오전 11:00 ~ 오후 8:00")
            .storeImage("s3-store-img-url")
            .avgRate(5.0)
            .build();
        PageRequest pageable = PageRequest.of(0, 10);
        SliceImpl<StoreResponse> slice = new SliceImpl<>(List.of(response), pageable, false);

        SliceResponse<StoreResponse> data = SliceResponse.of(slice);

        return ApiResponse.ok(data);
    }
}
