package com.foodwallet.server.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberWithdrawalRequest {

    private String pwd;

    @Builder
    private MemberWithdrawalRequest(String pwd) {
        this.pwd = pwd;
    }
}
