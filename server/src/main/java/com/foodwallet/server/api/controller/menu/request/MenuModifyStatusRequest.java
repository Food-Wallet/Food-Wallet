package com.foodwallet.server.api.controller.menu.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuModifyStatusRequest {

    private String status;

    @Builder
    private MenuModifyStatusRequest(String status) {
        this.status = status;
    }
}
