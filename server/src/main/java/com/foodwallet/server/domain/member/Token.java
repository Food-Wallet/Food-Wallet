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
public class Token {

    @Column(insertable = false, length = 100)
    private String fcmToken;

    @Column(insertable = false, length = 200)
    private String refreshToken;

    @Builder
    private Token(String fcmToken, String refreshToken) {
        this.fcmToken = fcmToken;
        this.refreshToken = refreshToken;
    }
}
