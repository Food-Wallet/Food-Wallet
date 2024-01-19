package com.foodwallet.server.domain.menu.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.menu.Menu;
import com.foodwallet.server.domain.menu.SellingStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

class MenuRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MenuRepository menuRepository;

    @DisplayName("메뉴 식별키로 메뉴 조회시 일치하는 데이터가 없으면 예외가 발생한다.")
    @Test
    void findByIdWithoutTuple() {
        //given //when //then
        assertThatThrownBy(() -> menuRepository.findById(1L))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("존재하지 않는 메뉴입니다.");
    }

    @DisplayName("메뉴 식별키로 메뉴늘 조회한다.")
    @Test
    void findById() {
        //given
        Menu menu = createMenu();

        //when
        Menu findMenu = menuRepository.findById(menu.getId());

        //then
        assertThat(findMenu)
            .extracting("name", "price", "status")
            .contains("간장닭강정", 8000, SellingStatus.SELLING);
    }

    private Menu createMenu() {
        Menu menu = Menu.builder()
            .name("간장닭강정")
            .price(8000)
            .status(SellingStatus.SELLING)
            .build();
        return menuRepository.save(menu);
    }

}