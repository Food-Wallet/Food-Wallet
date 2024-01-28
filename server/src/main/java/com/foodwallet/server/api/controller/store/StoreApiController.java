package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.store.request.*;
import com.foodwallet.server.api.service.store.StoreService;
import com.foodwallet.server.api.service.store.response.*;
import com.foodwallet.server.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreApiController {

    private final StoreService storeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<StoreCreateResponse> createStore(@Valid @ModelAttribute StoreCreateRequest request) {
        String email = SecurityUtils.getCurrentEmail();

        StoreCreateResponse response = storeService.createStore(email, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @PatchMapping("/{storeId}")
    public ApiResponse<StoreModifyInfoResponse> modifyStoreInfo(
        @PathVariable Long storeId,
        @Valid @RequestBody StoreModifyInfoRequest request
    ) {
        String email = SecurityUtils.getCurrentEmail();

        StoreModifyInfoResponse response = storeService.modifyStoreInfo(email, storeId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/{storeId}/image")
    public ApiResponse<StoreModifyImageResponse> modifyStoreImage(
        @PathVariable Long storeId,
        @Valid @ModelAttribute StoreModifyImageRequest request
    ) {
        String email = SecurityUtils.getCurrentEmail();

        StoreModifyImageResponse response = storeService.modifyStoreImage(email, storeId, request.getStoreImage());

        return ApiResponse.ok(response);
    }

    @PostMapping("/{storeId}/open")
    public ApiResponse<StoreOpenResponse> openStore(
        @PathVariable Long storeId,
        @Valid @RequestBody StoreOpenRequest request
    ) {
        String email = SecurityUtils.getCurrentEmail();

        StoreOpenResponse response = storeService.openStore(email, storeId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/{storeId}/close")
    public ApiResponse<StoreCloseResponse> closeStore(@PathVariable Long storeId) {
        String email = SecurityUtils.getCurrentEmail();

        LocalDateTime currentDateTime = LocalDateTime.now();
        StoreCloseResponse response = storeService.closeStore(email, storeId, currentDateTime);

        return ApiResponse.ok(response);
    }

    @PostMapping("/{storeId}/remove")
    public ApiResponse<StoreRemoveResponse> removeStore(
        @PathVariable Long storeId,
        @Valid @RequestBody StoreRemoveRequest request
    ) {
        String email = SecurityUtils.getCurrentEmail();

        LocalDateTime currentDateTime = LocalDateTime.now();
        StoreRemoveResponse response = storeService.removeStore(email, storeId, request.getCurrentPwd(), currentDateTime);

        return ApiResponse.ok(response);
    }
}
