package com.foodwallet.server.api.service.bookmark.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkCreateResponse {

    private final String storeName;

    @Builder
    private BookmarkCreateResponse(String storeName) {
        this.storeName = storeName;
    }
}
