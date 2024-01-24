package com.foodwallet.server.api.service.member.response;

import com.foodwallet.server.domain.member.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfoResponse {

    private final String email;
    private final String name;
    private final int birthYear;
    private final String gender;
    private final MemberRole role;
    private final AccountResponse account;

    @Builder
    private MemberInfoResponse(String email, String name, int birthYear, String gender, MemberRole role, String bankCode, String accountNumber) {
        this.email = email;
        this.name = name;
        this.birthYear = birthYear;
        this.gender = gender;
        this.role = role;
        this.account = AccountResponse.builder()
            .bankCode(bankCode)
            .accountNumber(accountNumber)
            .build();
    }
}
