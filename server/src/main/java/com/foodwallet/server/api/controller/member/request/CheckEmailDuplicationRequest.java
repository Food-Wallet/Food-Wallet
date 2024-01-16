package com.foodwallet.server.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckEmailDuplicationRequest {

    private String email;

    @Builder
    private CheckEmailDuplicationRequest(String email) {
        this.email = email;
    }
}
