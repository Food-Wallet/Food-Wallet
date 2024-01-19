package com.foodwallet.server.domain.menu.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.menu.Menu;
import com.foodwallet.server.domain.menu.SellingStatus;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class MenuRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("메뉴 식별키로 메뉴 조회시 일치하는 데이터가 없으면 예외가 발생한다.")
    @Test
    void findByIdWithoutTuple() {
        //given //when //then
        assertThatThrownBy(() -> menuRepository.findById(1L))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("존재하지 않는 메뉴입니다.");
    }

    @DisplayName("메뉴 식별키로 메뉴를 조회한다.")
    @Test
    void findById() {
        //given
        Menu menu = createMenu(null);

        //when
        Menu findMenu = menuRepository.findById(menu.getId());

        //then
        assertThat(findMenu)
            .extracting("name", "price", "status")
            .contains("간장닭강정", 8000, SellingStatus.SELLING);
    }

    @DisplayName("메뉴 식별키로 메뉴와 매장을 조회시 일치하는 데이터가 없으면 예외가 발생한다.")
    @Test
    void findJoinStoreByIdWithoutTuple() {
        //given //when //then
        assertThatThrownBy(() -> menuRepository.findJoinStoreById(1L))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("존재하지 않는 메뉴입니다.");
    }

    @DisplayName("메뉴 식별키로 메뉴와 매장을 조회시 매장이 null이면 예외가 발생한다.")
    @Test
    void findJoinStoreByIdWhenStoreIsNull() {
        //given
        Menu menu = createMenu(null);

        //when //then
        assertThatThrownBy(() -> menuRepository.findJoinStoreById(menu.getId()))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("존재하지 않는 메뉴입니다.");
    }

    @DisplayName("메뉴 식별키로 메뉴와 매장을 조회한다.")
    @Test
    void findJoinStoreById() {
        //given
        Store store = createStore();
        Menu menu = createMenu(store);

        //when
        Menu findMenu = menuRepository.findJoinStoreById(menu.getId());

        //then
        assertThat(findMenu.getStore())
            .extracting("status", "type", "name")
            .contains(StoreStatus.CLOSE, StoreType.CHICKEN, "나리닭강정");
    }

    private Store createStore() {
        Store store = Store.builder()
            .status(StoreStatus.CLOSE)
            .type(StoreType.CHICKEN)
            .name("나리닭강정")
            .build();
        return storeRepository.save(store);
    }

    private Menu createMenu(Store store) {
        Menu menu = Menu.builder()
            .name("간장닭강정")
            .price(8000)
            .status(SellingStatus.SELLING)
            .store(store)
            .build();
        return menuRepository.save(menu);
    }

}