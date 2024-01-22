package com.foodwallet.server.api.controller.member.request;

import com.foodwallet.server.domain.member.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    private String email;
    private String pwd;
    private String name;
    private Integer birthYear;
    private String gender;
    private MemberRole role;

    @Builder
    private MemberCreateRequest(String email, String pwd, String name, Integer birthYear, String gender, MemberRole role) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.birthYear = birthYear;
        this.gender = gender;
        this.role = role;
    }
}
