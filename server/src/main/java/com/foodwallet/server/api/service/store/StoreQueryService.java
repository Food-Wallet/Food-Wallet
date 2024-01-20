package com.foodwallet.server.api.service.store;

import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.service.store.response.StoreDetailResponse;
import com.foodwallet.server.domain.menu.repository.MenuQueryRepository;
import com.foodwallet.server.domain.menu.repository.response.MenuResponse;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreQueryRepository;
import com.foodwallet.server.domain.store.repository.dto.StoreDetailDto;
import com.foodwallet.server.domain.store.repository.dto.StoreSearchCond;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.util.StringUtils.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StoreQueryService {

    private final StoreQueryRepository storeQueryRepository;
    private final MenuQueryRepository menuQueryRepository;

    public SliceResponse<StoreResponse> searchStores(String storeType, String query, Pageable pageable) {
        StoreType type = toStoreType(storeType);

        StoreSearchCond cond = StoreSearchCond.create(type, query);

        Slice<StoreResponse> content = storeQueryRepository.findAllByCond(cond, pageable);

        return SliceResponse.of(content);
    }

    public StoreDetailResponse searchStore(Long storeId) {
        StoreDetailDto store = storeQueryRepository.findStoreDetailById(storeId);

        List<MenuResponse> menus = menuQueryRepository.findByStoreId(storeId);

        return StoreDetailResponse.of(store, menus);
    }

    private StoreType toStoreType(String storeType) {
        if (hasText(storeType)) {
            return StoreType.of(storeType);
        }
        return null;
    }
}
