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

import static com.foodwallet.server.common.constant.CommonConst.PAGE_SIZE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreQueryApiController {

    private final StoreQueryService storeQueryService;

    /**
     * 검색 조건에 따른 매장 목록 조회 API
     *
     * @param request 검색 조건
     * @return 검색 조건과 일치하는 매장 목록
     */
    @GetMapping
    public ApiResponse<SliceResponse<StoreResponse>> searchStores(@Valid StoreSearchRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), PAGE_SIZE);

        SliceResponse<StoreResponse> response = storeQueryService.searchStores(request.getType(), request.getQuery(), pageRequest);

        return ApiResponse.ok(response);
    }

    /**
     * 매장 식별키로 매장 상세 조회 API
     *
     * @param storeId 조회할 매장의 식별키
     * @return 조회된 매장 정보
     */
    @GetMapping("/{storeId}")
    public ApiResponse<StoreDetailResponse> searchStore(@PathVariable Long storeId) {
        StoreDetailResponse response = storeQueryService.searchStore(storeId);

        return ApiResponse.ok(response);
    }
}
