package com.foodwallet.server.api.service.bookmark.response;

import com.foodwallet.server.domain.store.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BookmarkCancelResponse {

    private final String storeName;

    @Builder
    private BookmarkCancelResponse(String storeName) {
        this.storeName = storeName;
    }

    public static BookmarkCancelResponse of(Store store) {
        return BookmarkCancelResponse.builder()
            .storeName(store.getName())
            .build();
    }
}
