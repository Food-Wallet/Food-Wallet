package com.foodwallet.server.api.service.member.response;

import com.foodwallet.server.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberWithdrawalResponse {

    private final String email;
    private final String name;
    private final LocalDateTime withdrawalDateTime;

    @Builder
    private MemberWithdrawalResponse(String email, String name, LocalDateTime withdrawalDateTime) {
        this.email = email;
        this.name = name;
        this.withdrawalDateTime = withdrawalDateTime;
    }

    public static MemberWithdrawalResponse of(Member member) {
        return MemberWithdrawalResponse.builder()
            .email(member.getEmail())
            .name(member.getName())
            .withdrawalDateTime(LocalDateTime.now())
            .build();
    }
}
