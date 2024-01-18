package com.foodwallet.server.domain.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.assertj.core.api.Assertions.*;

class StoreTypeTest {

    @DisplayName("매장 타입이 서비스에서 지원하는 타입인지 체크한다.")
    @Test
    void of() {
        //given
        String givenType = "CHICKEN";

        //when
        StoreType type = StoreType.of(givenType);

        //then
        assertThat(type).isEqualByComparingTo(CHICKEN);
    }

    @DisplayName("매장 타입이 서비스에서 지원하는 타입이 아니라면 예외가 발생한다.")
    @Test
    void ofNotSupportType() {
        //given
        String givenType = "BOOK";

        //when //then
        assertThatThrownBy(() -> StoreType.of(givenType))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("지원하지 않는 매장 타입입니다.");
    }
}