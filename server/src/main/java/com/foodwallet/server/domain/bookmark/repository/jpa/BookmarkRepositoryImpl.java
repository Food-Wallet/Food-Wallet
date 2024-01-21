package com.foodwallet.server.domain.bookmark.repository.jpa;

import com.foodwallet.server.domain.bookmark.Bookmark;
import com.foodwallet.server.domain.bookmark.BookmarkId;
import com.foodwallet.server.domain.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BookmarkRepositoryImpl implements BookmarkRepository {

    private final BookmarkJpaRepository bookmarkJpaRepository;

    @Override
    public Bookmark save(Bookmark bookmark) {
        return bookmarkJpaRepository.save(bookmark);
    }

    @Override
    public Bookmark findById(Long memberId, Long storeId) {
        BookmarkId bookmarkId = BookmarkId.builder()
            .member(memberId)
            .store(storeId)
            .build();

        Optional<Bookmark> findBookmark = bookmarkJpaRepository.findById(bookmarkId);
        if (findBookmark.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 즐겨찾기입니다.");
        }
        return findBookmark.get();
    }
}
