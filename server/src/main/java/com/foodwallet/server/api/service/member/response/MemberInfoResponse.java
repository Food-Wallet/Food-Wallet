package com.foodwallet.server.api.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfoResponse {

    private final String email;
    private final String name;
    private final int age;
    private final String gender;

    @Builder
    private MemberInfoResponse(String email, String name, int age, String gender) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
