package com.foodwallet.server.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Account {

    @Column(columnDefinition = "char(3)", length = 3)
    private String bankCode;

    @Column(length = 14)
    private String accountNumber;

    @Column(columnDefinition = "char(4)", length = 4)
    private String accountPwd;

    @Builder
    private Account(String bankCode, String accountNumber, String accountPwd) {
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
        this.accountPwd = accountPwd;
    }
}
