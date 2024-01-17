package com.foodwallet.server.api.controller.basket.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasketCreateRequest {

    private Long menuId;
    private Integer count;

    @Builder
    private BasketCreateRequest(Long menuId, Integer count) {
        this.menuId = menuId;
        this.count = count;
    }
}
