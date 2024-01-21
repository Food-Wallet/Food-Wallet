package com.foodwallet.server.domain.bookmark.repository;

import com.foodwallet.server.domain.bookmark.Bookmark;

public interface BookmarkRepository {

    Bookmark save(Bookmark bookmark);
}
