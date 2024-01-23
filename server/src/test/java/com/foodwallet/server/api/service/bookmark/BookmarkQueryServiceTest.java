package com.foodwallet.server.api.service.bookmark;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.domain.bookmark.Bookmark;
import com.foodwallet.server.domain.bookmark.repository.BookmarkRepository;
import com.foodwallet.server.domain.bookmark.repository.response.BookmarkResponse;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.ReviewInfo;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import static com.foodwallet.server.domain.member.MemberRole.USER;
import static com.foodwallet.server.domain.store.StoreStatus.*;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static com.foodwallet.server.domain.store.StoreType.SNACK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class BookmarkQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private BookmarkQueryService bookmarkQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @DisplayName("회원 이메일과 페이지 정보를 입력 받아 즐겨찾기한 매장의 정보를 조회할 수 있다.")
    @Test
    void searchBookmarks() {
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

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        SliceResponse<BookmarkResponse> response = bookmarkQueryService.searchBookmarks("dong82@naver.com", pageRequest);

        //then
        assertThat(response.getContent()).hasSize(3)
            .extracting("status", "type", "storeName")
            .containsExactly(
                tuple(REST.getText(), SNACK.getText(), "대왕 타코야끼"),
                tuple(OPEN.getText(), SNACK.getText(), "황금붕어빵"),
                tuple(CLOSE.getText(), CHICKEN.getText(), "나리닭강정")
            );
    }

    private Member createMember() {
        Member member = Member.builder()
            .email("dong82@naver.com")
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .birthYear(2015)
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
            .reviewInfo(ReviewInfo.createReviewInfo())
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