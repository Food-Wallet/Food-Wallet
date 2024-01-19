package com.foodwallet.server.domain.menu;

import com.foodwallet.server.domain.store.StoreType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.foodwallet.server.domain.menu.SellingStatus.SELLING;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SellingStatusTest {

    @DisplayName("판매 상태 타입이 서비스에서 지원하는 타입인지 체크한다.")
    @Test
    void of() {
        //given
        String givenType = "SELLING";

        //when
        SellingStatus type = SellingStatus.of(givenType);

        //then
        assertThat(type).isEqualByComparingTo(SELLING);
    }

    @DisplayName("판매 상태 타입이 서비스에서 지원하는 타입이 아니라면 예외가 발생한다.")
    @Test
    void ofNotSupportType() {
        //given
        String givenType = "BOOK";

        //when //then
        assertThatThrownBy(() -> SellingStatus.of(givenType))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("지원하지 않는 판매 상태 타입입니다.");
    }
}