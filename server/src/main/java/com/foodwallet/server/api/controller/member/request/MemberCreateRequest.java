package com.foodwallet.server.api.controller.member.request;

import com.foodwallet.server.api.service.member.request.MemberCreateServiceRequest;
import com.foodwallet.server.domain.member.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.*;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    @NotBlank(message = NOT_BLANK_EMAIL)
    private String email;

    @NotBlank(message = NOT_BLANK_PASSWORD)
    private String pwd;

    @NotBlank(message = NOT_BLANK_NAME)
    private String name;

    @Positive(message = POSITIVE_BIRTH_YEAR)
    private Integer birthYear;

    @NotBlank(message = NOT_BLANK_GENDER)
    private String gender;

    @NotNull(message = NOT_BLANK_ROLE)
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
