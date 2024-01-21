package com.foodwallet.server.api.service.menu.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class MenuCreateServiceRequest {

    private final String name;
    private final String description;
    private final int price;
    private final MultipartFile image;

    @Builder
    private MenuCreateServiceRequest(String name, String description, int price, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}
