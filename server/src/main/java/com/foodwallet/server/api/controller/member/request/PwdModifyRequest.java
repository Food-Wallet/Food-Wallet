package com.foodwallet.server.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_CURRENT_PWD;
import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_NEW_PWD;

@Getter
@NoArgsConstructor
public class PwdModifyRequest {

    @NotBlank(message = NOT_BLANK_CURRENT_PWD)
    private String currentPwd;

    @NotBlank(message = NOT_BLANK_NEW_PWD)
    private String newPwd;

    @Builder
    private PwdModifyRequest(String currentPwd, String newPwd) {
        this.currentPwd = currentPwd;
        this.newPwd = newPwd;
    }
}
