package com.foodwallet.server.api.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PwdModifyResponse {

    private final String email;

    @Builder
    private PwdModifyResponse(String email) {
        this.email = email;
    }
}
