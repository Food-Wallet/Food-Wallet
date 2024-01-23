package com.foodwallet.server.api.service.member.response;

import com.foodwallet.server.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberCreateResponse {

    private final String email;
    private final String name;
    private final LocalDateTime signupDateTime;

    @Builder
    private MemberCreateResponse(String email, String name, LocalDateTime signupDateTime) {
        this.email = email;
        this.name = name;
        this.signupDateTime = signupDateTime;
    }

    public static MemberCreateResponse of(Member member) {
        return MemberCreateResponse.builder()
            .email(member.getEmail())
            .name(member.getName())
            .signupDateTime(member.getCreatedDateTime())
            .build();
    }
}
