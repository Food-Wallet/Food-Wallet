package com.foodwallet.server.api.controller.menu.request;

import com.foodwallet.server.api.service.menu.request.MenuModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.NOT_BLANK_MENU_NAME;
import static com.foodwallet.server.common.message.ErrorMessage.POSITIVE_MENU_PRICE;

@Getter
@NoArgsConstructor
public class MenuModifyRequest {

    @NotBlank(message = NOT_BLANK_MENU_NAME)
    private String name;

    private String description;

    @Positive(message = POSITIVE_MENU_PRICE)
    private Integer price;

    @Builder
    private MenuModifyRequest(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public MenuModifyServiceRequest toServiceRequest() {
        return MenuModifyServiceRequest.builder()
            .name(name)
            .description(description)
            .price(price)
            .build();
    }
}
