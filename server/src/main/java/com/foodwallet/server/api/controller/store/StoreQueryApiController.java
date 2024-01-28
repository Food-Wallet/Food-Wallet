package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.service.store.StoreQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreQueryApiController {

    private final StoreQueryService storeQueryService;
}
