package com.foodwallet.server.api.controller.store.request;

import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_STORE_TYPE;
import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_STORE_NAME;

@Getter
@NoArgsConstructor
public class StoreCreateRequest {

    @NotBlank(message = NOT_BLANK_STORE_TYPE)
    private String type;

    @NotBlank(message = NOT_BLANK_STORE_NAME)
    private String name;

    private String description;

    @Builder
    private StoreCreateRequest(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public StoreCreateServiceRequest toServiceRequest() {
        return StoreCreateServiceRequest.builder()
            .type(type)
            .name(name)
            .description(description)
            .build();
    }
}
