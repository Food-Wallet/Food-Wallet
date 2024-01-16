package com.foodwallet.server.api.controller.menu;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.menu.request.MenuCreateRequest;
import com.foodwallet.server.api.service.menu.response.MenuCreateResponse;
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
}
