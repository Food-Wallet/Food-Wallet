package com.foodwallet.server.api.controller.store.request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class StoreSearchRequest {

    private String type;

    private String query;

    @Positive(message = "페이지 번호는 양수여야 합니다.")
    private Integer page;

    @Builder
    private StoreSearchRequest(String type, String query, Integer page) {
        this.type = type;
        this.query = query;
        this.page = page;
    }
}
