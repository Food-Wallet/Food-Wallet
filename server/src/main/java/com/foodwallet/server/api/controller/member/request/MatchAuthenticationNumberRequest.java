package com.foodwallet.server.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_AUTHENTICATION_NUMBER;

@Getter
@NoArgsConstructor
public class MatchAuthenticationNumberRequest {

    @NotBlank(message = NOT_BLANK_AUTHENTICATION_NUMBER)
    private String authenticationNumber;

    @Builder
    private MatchAuthenticationNumberRequest(String authenticationNumber) {
        this.authenticationNumber = authenticationNumber;
    }
}
