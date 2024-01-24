package com.foodwallet.server.api.service.member.response;

import com.foodwallet.server.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PwdModifyResponse {

    private final String email;

    @Builder
    private PwdModifyResponse(String email) {
        this.email = email;
    }

    public static PwdModifyResponse of(Member member) {
        return PwdModifyResponse.builder()
            .email(member.getEmail())
            .build();
    }
}
