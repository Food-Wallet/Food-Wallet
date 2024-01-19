package com.foodwallet.server.api.controller.menu.request;

import com.foodwallet.server.api.service.menu.request.MenuCreateServiceRequest;
import com.foodwallet.server.domain.UploadFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
public class MenuCreateRequest {

    @NotBlank(message = "메뉴명은 필수입니다.")
    private String name;

    private String description;

    @Positive(message = "메뉴 가격은 양수여야 합니다.")
    private Integer price;

    private MultipartFile image;

    @Builder
    private MenuCreateRequest(String name, String description, Integer price, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public MenuCreateServiceRequest toServiceRequest(UploadFile image) {
        return MenuCreateServiceRequest.builder()
            .name(name)
            .description(description)
            .price(price)
            .image(image)
            .build();
    }
}
