package com.foodwallet.server.api.controller.store.request;

import com.foodwallet.server.api.service.store.request.StoreModifyInfoServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_STORE_NAME;
import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_STORE_TYPE;

@Getter
@NoArgsConstructor
public class StoreModifyInfoRequest {

    @NotBlank(message = NOT_BLANK_STORE_TYPE)
    private String storeType;

    @NotBlank(message = NOT_BLANK_STORE_NAME)
    private String storeName;

    private String storeDescription;

    @Builder
    private StoreModifyInfoRequest(String storeType, String storeName, String storeDescription) {
        this.storeType = storeType;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
    }

    public StoreModifyInfoServiceRequest toServiceRequest() {
        return StoreModifyInfoServiceRequest.builder()
            .type(storeType)
            .name(storeName)
            .description(storeDescription)
            .build();
    }
}
