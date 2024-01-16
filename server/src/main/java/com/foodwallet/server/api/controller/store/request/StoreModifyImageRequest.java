package com.foodwallet.server.api.controller.store.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class StoreModifyImageRequest {

    private MultipartFile image;

    @Builder
    private StoreModifyImageRequest(MultipartFile image) {
        this.image = image;
    }
}
