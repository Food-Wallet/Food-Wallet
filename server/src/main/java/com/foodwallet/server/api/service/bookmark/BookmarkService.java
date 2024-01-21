package com.foodwallet.server.api.service.bookmark;

import com.foodwallet.server.api.service.bookmark.response.BookmarkCreateResponse;
import com.foodwallet.server.domain.bookmark.Bookmark;
import com.foodwallet.server.domain.bookmark.repository.BookmarkRepository;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.Store;
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

    /**
     * 매장 식별키를 입력 받아 즐겨찾기를 등록한다. 즐겨찾기 등록 후 매장의 즐겨찾기 수는 1만큼 증가한다.
     *
     * @param email   즐겨찾기 등록할 회원의 이메일
     * @param storeId 즐겨찾기 등록할 매장의 식별키
     * @return 즐겨찾기 등록된 매장의 정보
     */
    public BookmarkCreateResponse createBookmark(String email, Long storeId) {
        Member member = memberRepository.findByEmail(email);

        Store store = storeRepository.findById(storeId);

        Bookmark bookmark = Bookmark.builder()
            .member(member)
            .store(store)
            .build();
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);

        store.increaseBookmarkCount();

        return BookmarkCreateResponse.of(savedBookmark.getStore());
    }
}
