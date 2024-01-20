package com.foodwallet.server.domain.store.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.dto.StoreSearchCond;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import static com.foodwallet.server.domain.store.StoreStatus.*;
import static com.foodwallet.server.domain.store.StoreStatus.CLOSE;
import static com.foodwallet.server.domain.store.StoreStatus.OPEN;
import static com.foodwallet.server.domain.store.StoreType.*;
import static org.assertj.core.api.Assertions.*;

class StoreQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private StoreQueryRepository storeQueryRepository;

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("매장 타입과 매장명을 입력 받아 매장 목록을 조회한다.")
    @Test
    void findAllByCond() {
        //given
        Store store1 = createStore(OPEN, CHICKEN, "나리닭강정");
        Store store2 = createStore(OPEN, CHICKEN, "닭강정공작소");
        Store store3 = createStore(OPEN, CHICKEN, "마약닭강정집");
        Store store4 = createStore(OPEN, CHICKEN, "전기통치킨");
        Store store5 = createStore(CLOSE, CHICKEN, "나리닭강정");
        Store store6 = createStore(REST, CHICKEN, "나리닭강정");
        Store store7 = createStore(OPEN, SNACK, "황금붕어빵");

        StoreSearchCond cond = StoreSearchCond.builder()
            .type(CHICKEN)
            .query("닭강정")
            .build();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Slice<StoreResponse> responses = storeQueryRepository.findAllByCond(cond, pageRequest);

        //then
        assertThat(responses.getContent()).hasSize(3)
            .extracting("type", "name")
            .containsExactlyInAnyOrder(
                tuple("치킨", "나리닭강정"),
                tuple("치킨", "닭강정공작소"),
                tuple("치킨", "마약닭강정집")
            );
    }

    @DisplayName("매장 타입과 매장명을 입력 받아 매장 목록을 조회한다. 매장 타입이 없는 경우 모두 조회한다.")
    @Test
    void findAllByCondWithoutType() {
        //given
        Store store1 = createStore(OPEN, CHICKEN, "나리닭강정");
        Store store2 = createStore(OPEN, CHICKEN, "닭강정공작소");
        Store store3 = createStore(OPEN, CHICKEN, "마약닭강정집");
        Store store4 = createStore(OPEN, CHICKEN, "전기통치킨");
        Store store5 = createStore(CLOSE, CHICKEN, "나리닭강정");
        Store store6 = createStore(REST, CHICKEN, "나리닭강정");
        Store store7 = createStore(OPEN, SNACK, "황금붕어빵");

        StoreSearchCond cond = StoreSearchCond.builder()
            .query("닭강정")
            .build();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Slice<StoreResponse> responses = storeQueryRepository.findAllByCond(cond, pageRequest);

        //then
        assertThat(responses.getContent()).hasSize(3)
            .extracting("type", "name")
            .containsExactlyInAnyOrder(
                tuple("치킨", "나리닭강정"),
                tuple("치킨", "닭강정공작소"),
                tuple("치킨", "마약닭강정집")
            );
    }

    @DisplayName("매장 타입과 매장명을 입력 받아 매장 목록을 조회한다. 매장명이 없는 경우 모두 조회한다.")
    @Test
    void findAllByCondWithoutName() {
        //given
        Store store1 = createStore(OPEN, CHICKEN, "나리닭강정");
        Store store2 = createStore(OPEN, CHICKEN, "닭강정공작소");
        Store store3 = createStore(OPEN, CHICKEN, "마약닭강정집");
        Store store4 = createStore(OPEN, CHICKEN, "전기통치킨");
        Store store5 = createStore(CLOSE, CHICKEN, "나리닭강정");
        Store store6 = createStore(REST, CHICKEN, "나리닭강정");
        Store store7 = createStore(OPEN, SNACK, "황금붕어빵");

        StoreSearchCond cond = StoreSearchCond.builder()
            .type(CHICKEN)
            .build();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Slice<StoreResponse> responses = storeQueryRepository.findAllByCond(cond, pageRequest);

        //then
        assertThat(responses.getContent()).hasSize(4)
            .extracting("type", "name")
            .containsExactlyInAnyOrder(
                tuple("치킨", "나리닭강정"),
                tuple("치킨", "닭강정공작소"),
                tuple("치킨", "마약닭강정집"),
                tuple("치킨", "전기통치킨")
            );
    }

    @DisplayName("매장 타입과 매장명을 입력 받아 매장 목록을 조회한다. 매장 타입과 매정명이 없는 경우 모두 조회한다.")
    @Test
    void findAllByCondWithoutCond() {
        //given
        Store store1 = createStore(OPEN, CHICKEN, "나리닭강정");
        Store store2 = createStore(OPEN, CHICKEN, "닭강정공작소");
        Store store3 = createStore(OPEN, CHICKEN, "마약닭강정집");
        Store store4 = createStore(OPEN, CHICKEN, "전기통치킨");
        Store store5 = createStore(CLOSE, CHICKEN, "나리닭강정");
        Store store6 = createStore(REST, CHICKEN, "나리닭강정");
        Store store7 = createStore(OPEN, SNACK, "황금붕어빵");

        StoreSearchCond cond = StoreSearchCond.builder()
            .build();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Slice<StoreResponse> responses = storeQueryRepository.findAllByCond(cond, pageRequest);

        //then
        assertThat(responses.getContent()).hasSize(5)
            .extracting("type", "name")
            .containsExactlyInAnyOrder(
                tuple("치킨", "나리닭강정"),
                tuple("치킨", "닭강정공작소"),
                tuple("치킨", "마약닭강정집"),
                tuple("치킨", "전기통치킨"),
                tuple("간식", "황금붕어빵")
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