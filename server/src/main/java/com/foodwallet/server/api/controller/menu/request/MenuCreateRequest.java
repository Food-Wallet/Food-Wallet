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

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_MENU_NAME;
import static com.foodwallet.server.common.message.ErrorMessage.POSITIVE_MENU_PRICE;

@Getter @Setter
@NoArgsConstructor
public class MenuCreateRequest {

    @NotBlank(message = NOT_BLANK_MENU_NAME)
    private String name;

    private String description;

    @Positive(message = POSITIVE_MENU_PRICE)
    private Integer price;

    private MultipartFile image;

    @Builder
    private MenuCreateRequest(String name, String description, Integer price, MultipartFile image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public MenuCreateServiceRequest toServiceRequest() {
        return MenuCreateServiceRequest.builder()
            .name(name)
            .description(description)
            .price(price)
            .image(image)
            .build();
    }
}
