package com.foodwallet.server.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_CURRENT_PWD;

@Getter
@NoArgsConstructor
public class MemberWithdrawalRequest {

    @NotBlank(message = NOT_BLANK_CURRENT_PWD)
    private String pwd;

    @Builder
    private MemberWithdrawalRequest(String pwd) {
        this.pwd = pwd;
    }
}
