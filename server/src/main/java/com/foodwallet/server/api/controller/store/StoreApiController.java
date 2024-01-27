package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.api.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreApiController {

    private final StoreService storeService;
}
