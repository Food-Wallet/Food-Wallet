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

    /**
     * 검색 조건을 입력 받아 매장 목록을 조회한다.
     *
     * @param storeType 매장 타입 조건
     * @param query     매장명 조건
     * @param pageable  페이징 정보
     * @return 조회된 매장 목록 및 슬라이스 정보
     */
    public SliceResponse<StoreResponse> searchStores(String storeType, String query, Pageable pageable) {
        StoreType type = toStoreType(storeType);

        StoreSearchCond cond = StoreSearchCond.create(type, query);

        Slice<StoreResponse> content = storeQueryRepository.findAllByCond(cond, pageable);

        return SliceResponse.of(content);
    }

    /**
     * 매장 식별키와 일치하는 매장 정보와 메뉴 목록를 조회한다.
     *
     * @param storeId 매장의 식별키
     * @return 조회된 매장 정보
     */
    public StoreDetailResponse searchStore(Long storeId) {
        StoreDetailDto store = storeQueryRepository.findStoreDetailById(storeId);

        List<MenuResponse> menus = menuQueryRepository.findByStoreId(storeId);

        return StoreDetailResponse.of(store, menus);
    }

    /**
     * 문자열 매장 타입을 StoreType 객체로 변환한다.
     *
     * @param storeType 변환할 매장 타입
     * @return StoreType형 매장 타입
     * @throws IllegalArgumentException 입력된 문자열 매장 타입과 일치하는 StoreType 매장 타입이 없는 경우
     */
    private StoreType toStoreType(String storeType) {
        if (hasText(storeType)) {
            return StoreType.of(storeType);
        }
        return null;
    }
}
