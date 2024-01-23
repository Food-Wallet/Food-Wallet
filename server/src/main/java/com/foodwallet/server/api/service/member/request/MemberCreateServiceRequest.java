package com.foodwallet.server.api.service.member.request;

import com.foodwallet.server.domain.member.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberCreateServiceRequest {

    private final String email;
    private final String pwd;
    private final String name;
    private final Integer birthYear;
    private final String gender;
    private final MemberRole role;

    @Builder
    private MemberCreateServiceRequest(String email, String pwd, String name, Integer birthYear, String gender, MemberRole role) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.birthYear = birthYear;
        this.gender = gender;
        this.role = role;
    }
}
