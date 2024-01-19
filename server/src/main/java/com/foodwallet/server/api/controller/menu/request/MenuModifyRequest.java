package com.foodwallet.server.api.controller.menu.request;

import com.foodwallet.server.api.service.menu.request.MenuModifyServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuModifyRequest {

    @NotBlank(message = "메뉴명은 필수입니다.")
    private String name;

    private String description;

    @Positive(message = "메뉴 가격은 양수여야 합니다.")
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
