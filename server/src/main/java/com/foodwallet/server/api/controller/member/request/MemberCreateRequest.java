package com.foodwallet.server.api.controller.member.request;

import com.foodwallet.server.api.service.member.request.MemberCreateServiceRequest;
import com.foodwallet.server.domain.member.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String pwd;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @Positive(message = "출생연도를 정확하게 입력하세요.")
    private Integer birthYear;

    @NotBlank(message = "성별을 입력하세요.")
    private String gender;

    @NotNull(message = "회원 구분을 입력하세요.")
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

    public MemberCreateServiceRequest toServiceRequest() {
        return MemberCreateServiceRequest.builder()
            .email(email)
            .pwd(pwd)
            .name(name)
            .birthYear(birthYear)
            .gender(gender)
            .role(role)
            .build();
    }
}
