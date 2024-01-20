package com.foodwallet.server.api.service.store;

import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StoreQueryService {

    public SliceResponse<StoreResponse> searchStores(String type, String query, Pageable pageable) {
        return null;
    }
}
