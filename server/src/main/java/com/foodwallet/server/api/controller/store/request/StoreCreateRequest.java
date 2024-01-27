package com.foodwallet.server.api.controller.store.request;

import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_STORE_TYPE;
import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_STORE_NAME;

@Getter @Setter
@NoArgsConstructor
public class StoreCreateRequest {

    @NotBlank(message = NOT_BLANK_STORE_TYPE)
    private String storeType;

    @NotBlank(message = NOT_BLANK_STORE_NAME)
    private String storeName;

    private String storeDescription;

    private MultipartFile storeImage;

    @Builder
    private StoreCreateRequest(String storeType, String storeName, String storeDescription, MultipartFile storeImage) {
        this.storeType = storeType;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.storeImage = storeImage;
    }

    public StoreCreateServiceRequest toServiceRequest() {
        return StoreCreateServiceRequest.builder()
            .type(storeType)
            .name(storeName)
            .description(storeDescription)
            .image(storeImage)
            .build();
    }
}
