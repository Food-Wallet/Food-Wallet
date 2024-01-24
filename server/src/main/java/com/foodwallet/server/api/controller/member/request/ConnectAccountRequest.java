package com.foodwallet.server.api.controller.member.request;

import com.foodwallet.server.api.service.member.request.ConnectAccountServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.*;

@Getter
@NoArgsConstructor
public class ConnectAccountRequest {

    @NotBlank(message = NOT_BLANK_BANK_CODE)
    private String bankCode;

    @NotBlank(message = NOT_BLANK_ACCOUNT_NUMBER)
    private String accountNumber;

    @NotBlank(message = NOT_BLANK_ACCOUNT_PWD)
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
