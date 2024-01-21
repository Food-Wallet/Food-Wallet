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

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreApiController {

    private final StoreService storeService;

    /**
     * 신규 매장 등록 API
     *
     * @param request 신규 매장 등록을 위한 매장 정보
     * @return 등록된 신규 매장의 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<StoreCreateResponse> createStore(@Valid @RequestBody StoreCreateRequest request) {
        String email = SecurityUtils.getCurrentEmail();

        StoreCreateResponse response = storeService.createStore(email, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    /**
     * 매장 정보 수정 API
     *
     * @param storeId 정보를 수정할 매장의 식별키
     * @param request 정보 수정을 위한 매장 정보
     * @return 수정된 매장의 정보
     */
    @PatchMapping("/{storeId}")
    public ApiResponse<StoreModifyResponse> modifyStoreInfo(
        @PathVariable Long storeId,
        @Valid @RequestBody StoreModifyRequest request
    ) {
        String email = SecurityUtils.getCurrentEmail();

        StoreModifyResponse response = storeService.modifyStoreInfo(email, storeId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    /**
     * 매장 운영 시작 API
     *
     * @param storeId 운영을 시작할 매장의 식별키
     * @param request 운영 시작시 등록되야 하는 위치와 시간 정보
     * @return 운영 시작한 매장의 정보
     */
    @PatchMapping("/{storeId}/open")
    public ApiResponse<StoreOpenResponse> openStore(
        @PathVariable Long storeId,
        @Valid @RequestBody StoreOpenRequest request
    ) {
        String email = SecurityUtils.getCurrentEmail();

        StoreOpenResponse response = storeService.openStore(email, storeId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    /**
     * 매장 운영 종료 API
     *
     * @param storeId 운영을 종료할 매장의 식별키
     * @return 운영 종료한 매장의 정보
     */
    @PatchMapping("/{storeId}/close")
    public ApiResponse<StoreCloseResponse> closeStore(@PathVariable Long storeId) {
        String email = SecurityUtils.getCurrentEmail();

        StoreCloseResponse response = storeService.closeStore(email, storeId);

        return ApiResponse.ok(response);
    }

    /**
     * 매장 이미지 수정 API
     *
     * @param storeId 이미지를 수정할 매장의 식별키
     * @param request 이미지 수정을 위한 매장 이미지 파일
     * @return 이미지가 수정된 매장의 정보
     * @throws IOException 이미지 파일 업로드 실패시 발생
     */
    @PostMapping("/{storeId}/image")
    public ApiResponse<StoreModifyImageResponse> modifyStoreImage(
        @PathVariable Long storeId,
        @Valid @ModelAttribute StoreModifyImageRequest request
    ) throws IOException {
        String email = SecurityUtils.getCurrentEmail();

        StoreModifyImageResponse response = storeService.modifyStoreImage(email, storeId, request.getImage());

        return ApiResponse.ok(response);
    }

    /**
     * 매장 영구 삭제 API
     *
     * @param storeId 영구 삭제할 매장의 식별키
     * @return 영구 삭제된 매장의 정보
     */
    @PostMapping("/{storeId}")
    public ApiResponse<StoreRemoveResponse> removeStore(
        @PathVariable Long storeId,
        @Valid @RequestBody StoreRemoveRequest request
    ) {
        String email = SecurityUtils.getCurrentEmail();

        StoreRemoveResponse response = storeService.removeStore(email, storeId, request.getPwd());

        return ApiResponse.ok(response);
    }
}
