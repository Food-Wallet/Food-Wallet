package com.foodwallet.server.api.service.store.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class StoreCreateServiceRequest {

    private final String type;
    private final String name;
    private final String description;
    private final MultipartFile image;

    @Builder
    private StoreCreateServiceRequest(String type, String name, String description, MultipartFile image) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.image = image;
    }
}
