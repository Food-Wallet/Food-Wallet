package com.foodwallet.server.api.controller.menu.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuModifyStatusRequest {

    @NotBlank(message = "판매 상태는 필수입니다.")
    private String status;

    @Builder
    private MenuModifyStatusRequest(String status) {
        this.status = status;
    }
}
