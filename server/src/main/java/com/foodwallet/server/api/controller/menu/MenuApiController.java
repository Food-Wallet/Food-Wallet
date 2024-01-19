package com.foodwallet.server.api.controller.menu;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.FileStore;
import com.foodwallet.server.api.controller.menu.request.MenuCreateRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyImageRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyStatusRequest;
import com.foodwallet.server.api.service.menu.MenuService;
import com.foodwallet.server.api.service.menu.response.*;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores/{storeId}/menus")
public class MenuApiController {

    private final MenuService menuService;
    private final FileStore fileStore;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MenuCreateResponse> createMenu(
        @PathVariable Long storeId,
        @Valid @ModelAttribute MenuCreateRequest request
    ) throws IOException {
        String email = SecurityUtils.getCurrentEmail();

        UploadFile uploadFile = fileStore.storeFile(request.getImage());

        MenuCreateResponse response = menuService.createMenu(email, storeId, request.toServiceRequest(uploadFile));

        return ApiResponse.created(response);
    }

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

    @PostMapping("/{menuId}/image")
    public ApiResponse<MenuModifyImageResponse> modifyMenuImage(
        @PathVariable Long storeId,
        @PathVariable Long menuId,
        @Valid @ModelAttribute MenuModifyImageRequest request
    ) throws IOException {
        String email = SecurityUtils.getCurrentEmail();

        UploadFile image = fileStore.storeFile(request.getImage());

        MenuModifyImageResponse response = menuService.modifyMenuImage(email, menuId, image);

        return ApiResponse.ok(response);
    }

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
