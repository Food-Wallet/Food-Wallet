package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.FileStore;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyImageRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyRequest;
import com.foodwallet.server.api.controller.store.request.StoreOpenRequest;
import com.foodwallet.server.domain.menu.SellingStatus;
import com.foodwallet.server.domain.menu.repository.response.MenuResponse;
import com.foodwallet.server.api.service.store.StoreService;
import com.foodwallet.server.api.service.store.response.*;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
