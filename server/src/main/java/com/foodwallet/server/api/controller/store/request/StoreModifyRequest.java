package com.foodwallet.server.api.controller.store.request;

import com.foodwallet.server.api.service.store.request.StoreModifyServiceRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreModifyRequest {

    @NotEmpty(message = "매장 타입은 필수입니다.")
    private String type;

    @NotEmpty(message = "매장명은 필수입니다.")
    private String name;

    private String description;

    @Builder
    private StoreModifyRequest(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public StoreModifyServiceRequest toServiceRequest() {
        return StoreModifyServiceRequest.builder()
            .type(type)
            .name(name)
            .description(description)
            .build();
    }
}
