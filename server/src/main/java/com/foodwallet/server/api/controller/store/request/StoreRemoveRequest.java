package com.foodwallet.server.api.controller.store.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_PASSWORD;

@Getter
@NoArgsConstructor
public class StoreRemoveRequest {

    @NotBlank(message = NOT_BLANK_PASSWORD)
    private String currentPwd;

    @Builder
    private StoreRemoveRequest(String currentPwd) {
        this.currentPwd = currentPwd;
    }
}
