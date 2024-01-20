package com.foodwallet.server.api.service.store;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static com.foodwallet.server.domain.store.StoreStatus.*;
import static com.foodwallet.server.domain.store.StoreStatus.OPEN;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static com.foodwallet.server.domain.store.StoreType.SNACK;
import static org.assertj.core.api.Assertions.*;

class StoreQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private StoreQueryService storeQueryService;

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("매장 타입, 매장명, 페이지 정보를 입력 받아 매장 목록을 조회할 수 있다.")
    @Test
    void searchStores() {
        //given
        Store store1 = createStore(OPEN, CHICKEN, "나리닭강정");
        Store store2 = createStore(OPEN, CHICKEN, "닭강정공작소");
        Store store3 = createStore(OPEN, CHICKEN, "마약닭강정집");
        Store store4 = createStore(OPEN, CHICKEN, "전기통치킨");
        Store store5 = createStore(CLOSE, CHICKEN, "나리닭강정");
        Store store6 = createStore(REST, CHICKEN, "나리닭강정");
        Store store7 = createStore(OPEN, SNACK, "황금붕어빵");

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        SliceResponse<StoreResponse> response = storeQueryService.searchStores("CHICKEN", "닭강정", pageRequest);

        //then
        assertThat(response)
            .extracting("currentPage", "size", "isFirst", "isLast")
            .contains(0, 10, true, true);
        assertThat(response.getContent()).hasSize(3)
            .extracting("type", "name")
            .containsExactlyInAnyOrder(
                tuple("치킨", "나리닭강정"),
                tuple("치킨", "닭강정공작소"),
                tuple("치킨", "마약닭강정집")
            );
    }

    private Store createStore(StoreStatus status, StoreType type, String name) {
        Store store = Store.builder()
            .status(status)
            .type(type)
            .name(name)
            .build();
        return storeRepository.save(store);
    }
}