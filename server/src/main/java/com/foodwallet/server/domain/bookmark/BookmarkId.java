package com.foodwallet.server.domain.bookmark;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class BookmarkId implements Serializable {

    private Long member;
    private Long store;

    @Builder
    private BookmarkId(Long member, Long store) {
        this.member = member;
        this.store = store;
    }
}
