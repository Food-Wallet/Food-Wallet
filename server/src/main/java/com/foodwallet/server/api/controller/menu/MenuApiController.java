package com.foodwallet.server.api.controller.menu;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.menu.request.MenuCreateRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyImageRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyStatusRequest;
import com.foodwallet.server.api.service.menu.MenuService;
import com.foodwallet.server.api.service.menu.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores/{storeId}/menus")
public class MenuApiController {

    private final MenuService menuService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MenuCreateResponse> createMenu(
        @PathVariable Long storeId,
        @Valid @ModelAttribute MenuCreateRequest request
    ) {
        MenuCreateResponse response = MenuCreateResponse.builder()
            .name("간장닭강정")
            .price(8000)
            .createdDateTime(LocalDateTime.of(2024, 1, 17, 9, 0))
            .build();
        return ApiResponse.created(response);
    }

    @PatchMapping("/{menuId}")
    public ApiResponse<MenuModifyResponse> modifyMenuInfo(
        @PathVariable Long storeId,
        @PathVariable Long menuId,
        @Valid @RequestBody MenuModifyRequest request
    ) {
        MenuModifyResponse response = MenuModifyResponse.builder()
            .name("간장닭강정")
            .description("우리 매장 시그니처 메뉴입니다!")
            .price(8000)
            .modifiedDateTime(LocalDateTime.of(2024, 1, 17, 9, 0))
            .build();
        return ApiResponse.ok(response);
    }

    @PostMapping("/{menuId}/image")
    public ApiResponse<MenuModifyImageResponse> modifyMenuImage(
        @PathVariable Long storeId,
        @PathVariable Long menuId,
        @Valid @ModelAttribute MenuModifyImageRequest request
    ) {
        MenuModifyImageResponse response = MenuModifyImageResponse.builder()
            .name("간장닭강정")
            .modifiedDateTime(LocalDateTime.of(2024, 1, 17, 9, 0))
            .build();
        return ApiResponse.ok(response);
    }

    @PatchMapping("/{menuId}/status")
    public ApiResponse<MenuModifyStatusResponse> modifyMenuStatus(
        @PathVariable Long storeId,
        @PathVariable Long menuId,
        @Valid @RequestBody MenuModifyStatusRequest request
    ) {
        MenuModifyStatusResponse response = MenuModifyStatusResponse.builder()
            .name("간장닭강정")
            .status("STOP_SELLING")
            .modifiedDateTime(LocalDateTime.of(2024, 1, 17, 9, 0))
            .build();
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{menuId}")
    public ApiResponse<MenuRemoveResponse> removeMenu(
        @PathVariable Long storeId,
        @PathVariable Long menuId
    ) {
        MenuRemoveResponse response = MenuRemoveResponse.builder()
            .name("간장닭강정")
            .removedDateTime(LocalDateTime.of(2024, 1, 17, 23, 0))
            .build();
        return ApiResponse.ok(response);
    }
}
