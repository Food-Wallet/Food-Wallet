package com.foodwallet.server.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.*;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = NOT_BLANK_EMAIL)
    private String email;

    @NotBlank(message = NOT_BLANK_PASSWORD)
    private String pwd;

    @NotBlank(message = NOT_BLANK_FCM_TOKEN)
    private String fcmToken;

    @Builder
    private LoginRequest(String email, String pwd, String fcmToken) {
        this.email = email;
        this.pwd = pwd;
        this.fcmToken = fcmToken;
    }
}
