package com.foodwallet.server.api.service.member.response;

import com.foodwallet.server.domain.member.ConnectAccount;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ConnectAccountResponse {

    private final String bankCode;
    private final String accountNumber;

    @Builder
    private ConnectAccountResponse(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }

    public static ConnectAccountResponse of(ConnectAccount connectAccount) {
        return ConnectAccountResponse.builder()
            .bankCode(connectAccount.getAccount().getBankCode())
            .accountNumber(connectAccount.getAccount().getAccountNumber())
            .build();
    }
}
