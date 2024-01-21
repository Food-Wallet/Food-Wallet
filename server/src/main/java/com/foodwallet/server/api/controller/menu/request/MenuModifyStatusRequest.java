package com.foodwallet.server.api.controller.menu.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_MENU_SELLING_STATUS;

@Getter
@NoArgsConstructor
public class MenuModifyStatusRequest {

    @NotBlank(message = NOT_BLANK_MENU_SELLING_STATUS)
    private String status;

    @Builder
    private MenuModifyStatusRequest(String status) {
        this.status = status;
    }
}
