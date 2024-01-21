package com.foodwallet.server.api.service.bookmark;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCancelResponse;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCreateResponse;
import com.foodwallet.server.domain.bookmark.Bookmark;
import com.foodwallet.server.domain.bookmark.repository.BookmarkRepository;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.foodwallet.server.domain.member.MemberRole.USER;
import static com.foodwallet.server.domain.store.StoreStatus.CLOSE;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.assertj.core.api.Assertions.*;

class BookmarkServiceTest extends IntegrationTestSupport {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @DisplayName("회원 이메일과 매장 식별키를 입력 받아 즐겨찾기에 등록한다.")
    @Test
    void createBookmark() {
        //given
        Member member = createMember();
        Store store = createStore();

        //when
        BookmarkCreateResponse response = bookmarkService.createBookmark("dong82@naver.com", store.getId());

        //then
        assertThat(response.getStoreName()).isEqualTo("나리닭강정");

        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore.getBookmarkCount()).isEqualTo(1);
    }

    @DisplayName("회원 이메일과 매장 식별키를 입력 받아 즐겨찾기를 취소한다.")
    @Test
    void cancelBookmark() {
        //given
        Member member = createMember();
        Store store = createStore();
        Bookmark bookmark = createBookmark(member, store);

        //when
        BookmarkCancelResponse response = bookmarkService.cancelBookmark("dong82@naver.com", store.getId());

        //then
        assertThat(response.getStoreName()).isEqualTo("나리닭강정");

        Bookmark findBookmark = bookmarkRepository.findById(member.getId(), store.getId());
        assertThat(findBookmark.isDeleted()).isTrue();
    }

    private Member createMember() {
        Member member = Member.builder()
            .email("dong82@naver.com")
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .age(10)
            .gender("F")
            .role(USER)
            .token(null)
            .build();
        return memberRepository.save(member);
    }

    private Store createStore() {
        Store store = Store.builder()
            .status(CLOSE)
            .type(CHICKEN)
            .name("나리닭강정")
            .build();
        return storeRepository.save(store);
    }

    private Bookmark createBookmark(Member member, Store store) {
        Bookmark bookmark = Bookmark.builder()
            .member(member)
            .store(store)
            .build();
        return bookmarkRepository.save(bookmark);
    }
}