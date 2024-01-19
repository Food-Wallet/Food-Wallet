package com.foodwallet.server.api.controller.store.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@NoArgsConstructor
public class StoreModifyImageRequest {

    private MultipartFile image;

    @Builder
    private StoreModifyImageRequest(MultipartFile image) {
        this.image = image;
    }
}
