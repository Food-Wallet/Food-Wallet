package com.foodwallet.server.api.service.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckEmailDuplicationResponse {

    private final Boolean isDuplicated;

    @Builder
    private CheckEmailDuplicationResponse(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
}
