package com.foodwallet.server.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PwdModifyRequest {

    private String currentPwd;
    private String newPwd;

    @Builder
    private PwdModifyRequest(String currentPwd, String newPwd) {
        this.currentPwd = currentPwd;
        this.newPwd = newPwd;
    }
}
