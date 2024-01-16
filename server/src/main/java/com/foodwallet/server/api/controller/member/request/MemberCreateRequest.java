package com.foodwallet.server.api.controller.member.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    private String email;
    private String pwd;
    private String name;
    private Integer age;
    private String gender;

    @Builder
    private MemberCreateRequest(String email, String pwd, String name, Integer age, String gender) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
