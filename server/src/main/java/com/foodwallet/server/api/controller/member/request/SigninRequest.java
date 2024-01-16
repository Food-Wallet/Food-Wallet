package com.foodwallet.server.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SigninRequest {

    private String email;
    private String pwd;

    @Builder
    private SigninRequest(String email, String pwd) {
        this.email = email;
        this.pwd = pwd;
    }
}
