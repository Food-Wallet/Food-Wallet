package com.foodwallet.server.api.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountModifyResponse {

    private final String bankCode;
    private final String accountNumber;

    @Builder
    private AccountModifyResponse(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }
}
