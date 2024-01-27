package com.foodwallet.server.docs.store;

import com.foodwallet.server.api.controller.store.StoreApiController;
import com.foodwallet.server.api.service.store.StoreService;
import com.foodwallet.server.docs.RestDocsSupport;

import static org.mockito.Mockito.mock;

public class StoreApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/stores";

    private final StoreService storeService = mock(StoreService.class);

    @Override
    protected Object initController() {
        return new StoreApiController(storeService);
    }

}
