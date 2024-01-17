package com.foodwallet.server.api.controller.basket.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BasketModifyRequest {

    private Integer count;

    @Builder
    private BasketModifyRequest(Integer count) {
        this.count = count;
    }
}
