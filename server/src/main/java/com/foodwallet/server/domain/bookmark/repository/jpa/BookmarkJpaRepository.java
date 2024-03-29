package com.foodwallet.server.domain.bookmark.repository.jpa;

import com.foodwallet.server.domain.bookmark.Bookmark;
import com.foodwallet.server.domain.bookmark.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkJpaRepository extends JpaRepository<Bookmark, BookmarkId> {
}
