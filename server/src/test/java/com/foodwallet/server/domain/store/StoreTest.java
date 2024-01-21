package com.foodwallet.server.domain.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.foodwallet.server.domain.store.StoreStatus.CLOSE;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.assertj.core.api.Assertions.*;

class StoreTest {

    @DisplayName("매장 타입, 매장명, 매장 설명, 사업자 정보를 입력 받아 매장을 생성한다.")
    @Test
    void createStore() {
        //given

        //when
        Store store = Store.createStore(CHICKEN, "나리닭강정", "국내 1위 닭강정", null);

        //then
        assertThat(store)
            .extracting("status", "type", "name", "description", "reviewInfo.reviewCount", "reviewInfo.avgRate", "image", "operationalInfo")
            .contains(CLOSE, CHICKEN, "나리닭강정", "국내 1위 닭강정", 0, 0.0, null, null);
    }

    @DisplayName("매장을 생성할 때 매장명은 최대 20자이다.")
    @Test
    void createStoreWithNameLessThanEqualMaxSize() {
        //given
        String name = generateStr(20);

        //when
        Store store = Store.createStore(CHICKEN, name, "국내 1위 닭강정", null);

        //then
        assertThat(store.getName().length()).isEqualTo(20);
    }

    @DisplayName("매장을 생성할 때 매장명이 20자를 초과하면 예외가 발생한다.")
    @Test
    void createStoreWithNameGreaterThanMaxSize() {
        //given
        String name = generateStr(21);

        //when //then
        assertThatThrownBy(() -> Store.createStore(CHICKEN, name, "국내 1위 닭강정", null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("길이는 최대 20자 입니다.");
    }

    @DisplayName("매장을 생성할 때 매장 설명은 최대 200자이다.")
    @Test
    void createStoreWithDescriptionLessThanEqualMaxSize() {
        //given
        String description = generateStr(200);

        //when
        Store store = Store.createStore(CHICKEN, "나리닭강정", description, null);

        //then
        assertThat(store.getDescription().length()).isEqualTo(200);
    }

    @DisplayName("매장을 생성할 때 매장 설명이 200자를 초과하면 예외가 발생한다.")
    @Test
    void createStoreWithDescriptionGreaterThan() {
        //given
        String description = generateStr(201);

        //when //then
        assertThatThrownBy(() -> Store.createStore(CHICKEN, "나리닭강정", description, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("길이는 최대 200자 입니다.");
    }

    private String generateStr(int size) {
        return "a".repeat(size);
    }
}