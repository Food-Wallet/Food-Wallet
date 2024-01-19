package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.FileStore;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyImageRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyRequest;
import com.foodwallet.server.api.controller.store.request.StoreOpenRequest;
import com.foodwallet.server.api.service.menu.response.MenuResponse;
import com.foodwallet.server.api.service.store.StoreService;
import com.foodwallet.server.api.service.store.response.*;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreApiController {

    private final StoreService storeService;
    private final FileStore fileStore;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<StoreCreateResponse> createStore(@Valid @RequestBody StoreCreateRequest request) {
        String email = SecurityUtils.getCurrentEmail();

        StoreCreateResponse response = storeService.createStore(email, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @PatchMapping("/{storeId}/open")
    public ApiResponse<StoreOpenResponse> openStore(@Valid @RequestBody StoreOpenRequest request, @PathVariable Long storeId) {

        StoreOpenResponse response = storeService.openStore(storeId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{storeId}/close")
    public ApiResponse<StoreCloseResponse> closeStore(@PathVariable Long storeId) {
        String email = SecurityUtils.getCurrentEmail();

        StoreCloseResponse response = storeService.closeStore(email, storeId);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{storeId}")
    public ApiResponse<StoreModifyResponse> modifyStoreInfo(
        @PathVariable Long storeId,
        @Valid @RequestBody StoreModifyRequest request
    ) {
        String email = SecurityUtils.getCurrentEmail();

        StoreModifyResponse response = storeService.modifyStoreInfo(email, storeId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{storeId}")
    public ApiResponse<StoreRemoveResponse> removeStore(@PathVariable Long storeId) {
        String email = SecurityUtils.getCurrentEmail();

        StoreRemoveResponse response = storeService.removeStore(email, storeId);

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
    ) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(request.getImage());

        StoreModifyImageResponse response = storeService.modifyStoreImage(storeId, uploadFile);

        return ApiResponse.ok(response);
    }
}
