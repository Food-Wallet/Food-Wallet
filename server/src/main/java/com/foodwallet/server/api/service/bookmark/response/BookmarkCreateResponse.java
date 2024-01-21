package com.foodwallet.server.api.service.bookmark.response;

import com.foodwallet.server.domain.store.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkCreateResponse {

    private final String storeName;

    @Builder
    private BookmarkCreateResponse(String storeName) {
        this.storeName = storeName;
    }

    public static BookmarkCreateResponse of(Store store) {
        return BookmarkCreateResponse.builder()
            .storeName(store.getName())
            .build();
    }
}
