package com.foodwallet.server.domain.bookmark;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class BookmarkId implements Serializable {

    private Long member;
    private Long store;
}
