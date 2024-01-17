package com.foodwallet.server.api.controller.menu.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class MenuCreateRequest {

    private String name;
    private String description;
    private Integer price;
    private MultipartFile image;

    @Builder
    private MenuCreateRequest(String name, String description, Integer price, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}
