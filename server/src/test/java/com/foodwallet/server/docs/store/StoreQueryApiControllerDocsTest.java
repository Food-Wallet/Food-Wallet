package com.foodwallet.server.docs.store;

import com.foodwallet.server.api.controller.store.StoreQueryApiController;
import com.foodwallet.server.api.service.store.StoreQueryService;
import com.foodwallet.server.docs.RestDocsSupport;

import static org.mockito.Mockito.mock;

public class StoreQueryApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/stores";

    private final StoreQueryService storeQueryService = mock(StoreQueryService.class);

    @Override
    protected Object initController() {
        return new StoreQueryApiController(storeQueryService);
    }

}
