package com.foodwallet.server.api.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccountResponse {

    private final String bankCode;
    private final String accountNumber;

    @Builder
    private AccountResponse(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }
}
