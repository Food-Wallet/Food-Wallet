package com.foodwallet.server.api.controller.menu;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.menu.request.MenuCreateRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyImageRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyStatusRequest;
import com.foodwallet.server.api.service.menu.MenuService;
import com.foodwallet.server.api.service.menu.response.*;
import com.foodwallet.server.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores/{storeId}/menus")
public class MenuApiController {

    private final MenuService menuService;

    /**
     * 신규 메뉴 등록 API
     *
     * @param storeId 메뉴를 등록할 매장의 식별키
     * @param request 신규 메뉴 등록을 위한 메뉴 정보
     * @return 등록된 신규 메뉴의 정보
     * @throws IOException 이미지 파일 업로드에 실패한 경우
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MenuCreateResponse> createMenu(
        @PathVariable Long storeId,
        @Valid @ModelAttribute MenuCreateRequest request
    ) throws IOException {
        String email = SecurityUtils.getCurrentEmail();

        MenuCreateResponse response = menuService.createMenu(email, storeId, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    /**
     * 메뉴 정보 수정 API
     *
     * @param storeId 정보를 수정할 메뉴를 판매하는 매장의 식별키
     * @param menuId  정보를 수정할 메뉴의 식별키
     * @param request 정보 수정을 위한 메뉴 정보
     * @return 수정된 메뉴의 정보
     */
    @PatchMapping("/{menuId}")
    public ApiResponse<MenuModifyResponse> modifyMenuInfo(
        @PathVariable Long storeId,
        @PathVariable Long menuId,
        @Valid @RequestBody MenuModifyRequest request
    ) {
        String email = SecurityUtils.getCurrentEmail();

        MenuModifyResponse response = menuService.modifyMenuInfo(email, menuId, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    /**
     * 메뉴 이미지 수정 API
     *
     * @param storeId 이미지를 수정할 메뉴를 판매하는 매장의 식별키
     * @param menuId  이미지를 수정할 메뉴의 식별키
     * @param request 이미지 수정을 위한 메뉴 이미지 파일
     * @return 이미지가 수정된 메뉴의 정보
     * @throws IOException 이미지 파일 업로드에 실패한 경우
     */
    @PostMapping("/{menuId}/image")
    public ApiResponse<MenuModifyImageResponse> modifyMenuImage(
        @PathVariable Long storeId,
        @PathVariable Long menuId,
        @Valid @ModelAttribute MenuModifyImageRequest request
    ) throws IOException {
        String email = SecurityUtils.getCurrentEmail();

        MenuModifyImageResponse response = menuService.modifyMenuImage(email, menuId, request.getImage());

        return ApiResponse.ok(response);
    }

    /**
     * 메뉴 판매 상태 수정 API
     *
     * @param storeId 판매 상태를 수정할 메뉴를 판매하는 매장의 식별키
     * @param menuId  판매 상태를 수정할 메뉴의 식별키
     * @param request 판매 상태 수정을 위한 판매 상태 정보
     * @return 판매 상태가 수정된 메뉴의 정보
     */
    @PatchMapping("/{menuId}/status")
    public ApiResponse<MenuModifyStatusResponse> modifyMenuStatus(
        @PathVariable Long storeId,
        @PathVariable Long menuId,
        @Valid @RequestBody MenuModifyStatusRequest request
    ) {
        String email = SecurityUtils.getCurrentEmail();

        MenuModifyStatusResponse response = menuService.modifyMenuStatus(email, menuId, request.getStatus());

        return ApiResponse.ok(response);
    }

    /**
     * 메뉴 영구 삭제 API
     *
     * @param storeId 영구 삭제할 메뉴를 판매하는 매장의 식별키
     * @param menuId  영구 삭제할 메뉴의 식별키
     * @return 영구 삭제된 메뉴의 정보
     */
    @DeleteMapping("/{menuId}")
    public ApiResponse<MenuRemoveResponse> removeMenu(
        @PathVariable Long storeId,
        @PathVariable Long menuId
    ) {
        String email = SecurityUtils.getCurrentEmail();

        MenuRemoveResponse response = menuService.removeMenu(email, menuId);

        return ApiResponse.ok(response);
    }
}
