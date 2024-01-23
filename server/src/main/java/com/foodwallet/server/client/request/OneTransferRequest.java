package com.foodwallet.server.client.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OneTransferRequest {

    private String bankCode;
    private String accountNumber;
    private String message;

    @Builder
    private OneTransferRequest(String bankCode, String accountNumber, String message) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.message = message;
    }
}
