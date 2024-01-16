package com.foodwallet.server.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountModifyRequest {

    private String bankCode;
    private String accountNumber;
    private String accountPwd;

    @Builder
    private AccountModifyRequest(String bankCode, String accountNumber, String accountPwd) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.accountPwd = accountPwd;
    }
}
