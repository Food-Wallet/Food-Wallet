package com.foodwallet.server.domain.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@RedisHash(value = "account", timeToLive = 300)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConnectAccount {

    @Id
    private String email;

    private Account account;

    private String authenticationNumber;

    private LocalDateTime createdDateTime;

    @Builder
    private ConnectAccount(String email, Account account, String authenticationNumber) {
        this.email = email;
        this.account = account;
        this.authenticationNumber = authenticationNumber;
        this.createdDateTime = LocalDateTime.now();
    }
}
