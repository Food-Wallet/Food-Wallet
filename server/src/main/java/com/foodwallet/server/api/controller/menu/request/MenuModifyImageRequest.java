package com.foodwallet.server.api.controller.menu.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
public class MenuModifyImageRequest {

    private MultipartFile image;

    @Builder
    private MenuModifyImageRequest(MultipartFile image) {
        this.image = image;
    }
}
