package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.controller.store.request.StoreSearchRequest;
import com.foodwallet.server.api.service.store.StoreQueryService;
import com.foodwallet.server.api.service.store.response.StoreDetailResponse;
import com.foodwallet.server.domain.menu.SellingStatus;
import com.foodwallet.server.domain.menu.repository.response.MenuResponse;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        PageRequest pageRequest = PageRequest.of(request.getPage(), 10);

        SliceResponse<StoreResponse> response = storeQueryService.searchStores(request.getType(), request.getQuery(), pageRequest);

        return ApiResponse.ok(response);
    }

    @GetMapping("/{storeId}")
    public ApiResponse<StoreDetailResponse> searchStore(@PathVariable Long storeId) {
        MenuResponse menu1 = MenuResponse.builder()
            .menuId(1L)
            .name("간장닭강정")
            .description("우리 매장 시그니처 메뉴입니다!")
            .price(8000)
            .status(SellingStatus.SELLING)
            .menuImage("s3-store-menu-img-url")
            .build();
        MenuResponse menu2 = MenuResponse.builder()
            .menuId(2L)
            .name("양념닭강정")
            .description(null)
            .price(9000)
            .status(SellingStatus.HOLD)
            .menuImage("s3-store-menu-img-url")
            .build();
        StoreDetailResponse response = StoreDetailResponse.builder()
            .storeId(1L)
            .type("치킨")
            .name("나리닭강정")
            .description("대한민국에서 1등 닭강정!")
            .storeImage("s3-store-img-url")
            .avgRate(5.0)
            .status("OPEN")
            .address("서울 중구 세종대로 110")
            .openTime("오전 11:00 ~ 오후 8:00")
            .menus(List.of(menu1, menu2))
            .build();
        return ApiResponse.ok(response);
    }
}
