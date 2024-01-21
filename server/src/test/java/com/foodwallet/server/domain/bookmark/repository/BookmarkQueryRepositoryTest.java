package com.foodwallet.server.domain.bookmark.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.bookmark.response.BookmarkResponse;
import com.foodwallet.server.domain.bookmark.Bookmark;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;

import static com.foodwallet.server.domain.member.MemberRole.USER;
import static com.foodwallet.server.domain.store.StoreStatus.*;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static com.foodwallet.server.domain.store.StoreType.SNACK;
import static org.assertj.core.api.Assertions.*;

class BookmarkQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private BookmarkQueryRepository bookmarkQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @DisplayName("회원 식별키로 즐겨찾기 등록한 매장 정보를 조회한다.")
    @Test
    void findByMemberId() {
        //given
        Member member = createMember();
        Store store1 = createStore(CLOSE, CHICKEN, "나리닭강정");
        Store store2 = createStore(OPEN, SNACK, "황금붕어빵");
        Store store3 = createStore(REST, SNACK, "대왕 타코야끼");
        Store store4 = createStore(CLOSE, CHICKEN, "나리닭강정");
        store4.remove();

        Bookmark bookmark1 = createBookmark(member, store1);
        Bookmark bookmark2 = createBookmark(member, store2);
        Bookmark bookmark3 = createBookmark(member, store3);
        Bookmark bookmark4 = createBookmark(member, store4);

        //when
        Slice<BookmarkResponse> response = bookmarkQueryRepository.findByMemberId(member.getId());

        //then
        assertThat(response.getContent()).hasSize(3)
            .extracting("status", "type", "name")
            .containsExactly(
                tuple(REST, SNACK, "대왕 타코야끼"),
                tuple(OPEN, SNACK, "황금붕어빵"),
                tuple(CLOSE, CHICKEN, "나리닭강정")
            );
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

    private Store createStore(StoreStatus status, StoreType type, String name) {
        Store store = Store.builder()
            .status(status)
            .type(type)
            .name(name)
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