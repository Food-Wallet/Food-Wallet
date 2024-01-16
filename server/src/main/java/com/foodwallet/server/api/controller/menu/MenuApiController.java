package com.foodwallet.server.api.controller.menu;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.menu.request.MenuCreateRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyImageRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyRequest;
import com.foodwallet.server.api.service.menu.response.MenuCreateResponse;
import com.foodwallet.server.api.service.menu.response.MenuModifyImageResponse;
import com.foodwallet.server.api.service.menu.response.MenuModifyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores/{storeId}/menus")
public class MenuApiController {

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
}
