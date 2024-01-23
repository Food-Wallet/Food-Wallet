package com.foodwallet.server.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String pwd;
    private String fcmToken;

    @Builder
    private LoginRequest(String email, String pwd, String fcmToken) {
        this.email = email;
        this.pwd = pwd;
        this.fcmToken = fcmToken;
    }
}
