package com.foodwallet.server.api.service.store;

import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class StoreService {

    public StoreCreateResponse createStore(String email, StoreCreateServiceRequest request) {
        return null;
    }
}
