package com.foodwallet.server.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckEmailDuplicationRequest {

    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    @Builder
    private CheckEmailDuplicationRequest(String email) {
        this.email = email;
    }
}
