package com.foodwallet.server.api.service.bookmark.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkCancelResponse {

    private final String storeName;

    @Builder
    private BookmarkCancelResponse(String storeName) {
        this.storeName = storeName;
    }
}
