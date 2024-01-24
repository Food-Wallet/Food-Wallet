package com.foodwallet.server.client.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OneTransferResponse {

    private String bankCode;
    private String accountNumber;

    @Builder
    private OneTransferResponse(String bankCode, String accountNumber) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }
}
