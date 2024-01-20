package com.foodwallet.server.api.service.store;

import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreQueryRepository;
import com.foodwallet.server.domain.store.repository.dto.StoreSearchCond;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.StringUtils.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StoreQueryService {

    private final StoreQueryRepository storeQueryRepository;

    public SliceResponse<StoreResponse> searchStores(String storeType, String query, Pageable pageable) {
        StoreType type = null;
        if (hasText(storeType)) {
            type = StoreType.of(storeType);
        }

        StoreSearchCond cond = StoreSearchCond.builder()
            .type(type)
            .query(query)
            .build();

        Slice<StoreResponse> content = storeQueryRepository.findAllByCond(cond, pageable);

        return SliceResponse.of(content);
    }
}
