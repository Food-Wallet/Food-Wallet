package com.foodwallet.server.api.service.menu.request;

import com.foodwallet.server.domain.UploadFile;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuCreateServiceRequest {

    private final String name;
    private final String description;
    private final int price;
    private final UploadFile image;

    @Builder
    private MenuCreateServiceRequest(String name, String description, int price, UploadFile image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}
