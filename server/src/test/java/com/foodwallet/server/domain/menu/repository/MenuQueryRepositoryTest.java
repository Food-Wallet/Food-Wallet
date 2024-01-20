package com.foodwallet.server.domain.menu.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.menu.Menu;
import com.foodwallet.server.domain.menu.SellingStatus;
import com.foodwallet.server.domain.menu.repository.response.MenuResponse;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.foodwallet.server.domain.menu.SellingStatus.*;
import static com.foodwallet.server.domain.store.StoreStatus.OPEN;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.assertj.core.api.Assertions.*;

class MenuQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MenuQueryRepository menuQueryRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("매장 식별키로 메뉴 목록을 조회한다.")
    @Test
    void findByStoreId() {
        //given
        Store store1 = createStore("나리닭강정");
        Menu menu1 = createMenu("간장닭강정", 8000, SELLING, store1);
        Menu menu2 = createMenu("양념닭강정", 9000, HOLD, store1);
        Menu menu3 = createMenu("마약닭강정", 10000, STOP_SELLING, store1);

        Store store2 = createStore("대한닭강정");
        Menu menu4 = createMenu("대한간장닭강정", 8000, SELLING, store2);
        Menu menu5 = createMenu("대한양념닭강정", 9000, HOLD, store2);
        Menu menu6 = createMenu("대한마약닭강정", 10000, STOP_SELLING, store2);

        //when
        List<MenuResponse> content = menuQueryRepository.findByStoreId(store1.getId());

        //then
        assertThat(content).hasSize(2)
            .extracting("name", "price", "status")
            .containsExactlyInAnyOrder(
                tuple("간장닭강정", 8000, SELLING.getText()),
                tuple("양념닭강정", 9000, HOLD.getText())
            );
    }

    @DisplayName("매장 식별키로 메뉴 목록을 조회한다. 일치하는 메뉴 목록이 없으면 빈 배열을 반환한다.")
    @Test
    void findByStoreIdWithoutTuple() {
        //given
        Store store1 = createStore("나리닭강정");

        Store store2 = createStore("대한닭강정");
        Menu menu1 = createMenu("대한간장닭강정", 8000, SELLING, store2);
        Menu menu2 = createMenu("대한양념닭강정", 9000, HOLD, store2);
        Menu menu3 = createMenu("대한마약닭강정", 10000, STOP_SELLING, store2);

        //when
        List<MenuResponse> content = menuQueryRepository.findByStoreId(store1.getId());

        //then
        assertThat(content).isEmpty();
    }

    private Store createStore(String name) {
        Store store = Store.builder()
            .status(OPEN)
            .type(CHICKEN)
            .name(name)
            .build();
        return storeRepository.save(store);
    }

    private Menu createMenu(String name, int price, SellingStatus status, Store store) {
        Menu menu = Menu.builder()
            .name(name)
            .price(price)
            .status(status)
            .store(store)
            .build();
        return menuRepository.save(menu);
    }

}