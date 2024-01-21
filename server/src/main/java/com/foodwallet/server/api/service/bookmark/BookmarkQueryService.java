package com.foodwallet.server.api.service.bookmark;

import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.domain.bookmark.repository.BookmarkQueryRepository;
import com.foodwallet.server.domain.bookmark.repository.response.BookmarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookmarkQueryService {

    private final BookmarkQueryRepository bookmarkQueryRepository;

    public SliceResponse<BookmarkResponse> searchBookmarks(String email, Pageable pageable) {
        return null;
    }
}
