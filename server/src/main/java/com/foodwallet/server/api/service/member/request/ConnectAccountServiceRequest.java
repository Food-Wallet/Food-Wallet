package com.foodwallet.server.api.service.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ConnectAccountServiceRequest {

    private final String bankCode;
    private final String accountNumber;
    private final String accountPwd;

    @Builder
    private ConnectAccountServiceRequest(String bankCode, String accountNumber, String accountPwd) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.accountPwd = accountPwd;
    }
}
