package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyImageRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyRequest;
import com.foodwallet.server.api.controller.store.request.StoreOpenRequest;
import com.foodwallet.server.api.service.menu.response.MenuResponse;
import com.foodwallet.server.api.service.store.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @PatchMapping("/{storeId}/close")
    public ApiResponse<StoreCloseResponse> closeStore(@PathVariable Long storeId) {
        StoreCloseResponse response = StoreCloseResponse.builder()
            .name("나리닭강정")
            .address("서울 중구 세종대로 110")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .closedDateTime(LocalDateTime.of(2023, 1, 16, 22, 0))
            .build();
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{storeId}")
    public ApiResponse<StoreModifyResponse> modifyStoreInfo(
        @PathVariable Long storeId,
        @Valid @RequestBody StoreModifyRequest request
    ) {
        StoreModifyResponse response = StoreModifyResponse.builder()
            .type("치킨")
            .name("나리닭강정")
            .description("대한민국에서 1등 닭강정!")
            .modifiedDateTime(LocalDateTime.of(2024, 1, 16, 9, 15))
            .build();
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{storeId}")
    public ApiResponse<StoreRemoveResponse> removeStore(@PathVariable Long storeId) {
        StoreRemoveResponse response = StoreRemoveResponse.builder()
            .type("치킨")
            .name("나리닭강정")
            .removedDateTime(LocalDateTime.of(2024, 1, 16, 20, 0))
            .build();
        return ApiResponse.ok(response);
    }

    @GetMapping
    public ApiResponse<SliceResponse<StoreResponse>> searchStores(
        @RequestParam String type,
        @RequestParam String query,
        @RequestParam Integer page
    ) {
        StoreResponse response = StoreResponse.builder()
            .storeId(1L)
            .type("치킨")
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

    @GetMapping("/{storeId}")
    public ApiResponse<StoreDetailResponse> searchStore(@PathVariable Long storeId) {
        MenuResponse menu1 = MenuResponse.builder()
            .menuId(1L)
            .name("간장닭강정")
            .description("우리 매장 시그니처 메뉴입니다!")
            .price(8000)
            .status("SELLING")
            .menuImage("s3-store-menu-img-url")
            .build();
        MenuResponse menu2 = MenuResponse.builder()
            .menuId(2L)
            .name("양념닭강정")
            .description(null)
            .price(9000)
            .status("HOLD")
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

    @PostMapping("/{storeId}/image")
    public ApiResponse<StoreModifyImageResponse> modifyStoreImage(
        @PathVariable Long storeId,
        @Valid @ModelAttribute StoreModifyImageRequest request
    ) {
        StoreModifyImageResponse response = StoreModifyImageResponse.builder()
            .type("치킨")
            .name("나리닭강정")
            .imageModifiedDateTime(LocalDateTime.of(2024, 1, 17, 9, 0))
            .build();
        return ApiResponse.ok(response);
    }
}
