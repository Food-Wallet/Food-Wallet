package com.foodwallet.server.api.controller.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_EMAIL;

@Getter
@NoArgsConstructor
public class CheckEmailDuplicationRequest {

    @NotBlank(message = NOT_BLANK_EMAIL)
    private String email;

    @Builder
    private CheckEmailDuplicationRequest(String email) {
        this.email = email;
    }
}
