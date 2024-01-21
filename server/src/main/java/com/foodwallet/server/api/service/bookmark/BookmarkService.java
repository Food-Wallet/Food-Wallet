package com.foodwallet.server.api.service.bookmark;

import com.foodwallet.server.api.service.bookmark.response.BookmarkCreateResponse;
import com.foodwallet.server.domain.bookmark.repository.BookmarkRepository;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public BookmarkCreateResponse createBookmark(String email, Long storeId) {
        return null;
    }
}
