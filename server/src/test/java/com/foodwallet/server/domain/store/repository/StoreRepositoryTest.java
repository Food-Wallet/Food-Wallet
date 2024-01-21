package com.foodwallet.server.domain.store.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.assertj.core.api.Assertions.*;

class StoreRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("매장 식별키로 매장 조회시 일치하는 데이터가 없으면 예외가 발생한다.")
    @Test
    void findByIdWithoutTuple() {
        //given //when //then
        assertThatThrownBy(() -> storeRepository.findById(1L))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("존재하지 않는 매장입니다.");
    }

    @DisplayName("매장 식별키로 매장을 조회한다.")
    @Test
    void findById() {
        //given
        Store store = createStore();

        //when
        Store findStore = storeRepository.findById(store.getId());

        //then
        assertThat(findStore)
            .extracting("type", "name")
            .contains(CHICKEN, "나리닭강정");
    }

    private Store createStore() {
        Store store = Store.builder()
            .status(StoreStatus.CLOSE)
            .type(CHICKEN)
            .name("나리닭강정")
            .build();
        return storeRepository.save(store);
    }

}