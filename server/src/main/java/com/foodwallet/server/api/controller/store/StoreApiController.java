package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import com.foodwallet.server.api.service.store.StoreService;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}
