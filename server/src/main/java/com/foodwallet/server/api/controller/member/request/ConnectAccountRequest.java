package com.foodwallet.server.api.controller.member.request;

import com.foodwallet.server.api.service.member.request.ConnectAccountServiceRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConnectAccountRequest {

    private String bankCode;
    private String accountNumber;
    private String accountPwd;

    @Builder
    private ConnectAccountRequest(String bankCode, String accountNumber, String accountPwd) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.accountPwd = accountPwd;
    }

    public ConnectAccountServiceRequest toServiceRequest() {
        return ConnectAccountServiceRequest.builder()
            .bankCode(bankCode)
            .accountNumber(accountNumber)
            .accountPwd(accountPwd)
            .build();
    }
}
