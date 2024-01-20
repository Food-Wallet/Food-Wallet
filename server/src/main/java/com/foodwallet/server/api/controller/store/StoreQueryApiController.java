package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.controller.store.request.StoreSearchRequest;
import com.foodwallet.server.api.service.store.StoreQueryService;
import com.foodwallet.server.api.service.store.response.StoreDetailResponse;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreQueryApiController {

    private final StoreQueryService storeQueryService;

    @GetMapping
    public ApiResponse<SliceResponse<StoreResponse>> searchStores(@Valid StoreSearchRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), 10);

        SliceResponse<StoreResponse> response = storeQueryService.searchStores(request.getType(), request.getQuery(), pageRequest);

        return ApiResponse.ok(response);
    }

    @GetMapping("/{storeId}")
    public ApiResponse<StoreDetailResponse> searchStore(@PathVariable Long storeId) {
        StoreDetailResponse response = storeQueryService.searchStore(storeId);

        return ApiResponse.ok(response);
    }
}
