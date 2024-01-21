package com.foodwallet.server.api.service.bookmark;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCreateResponse;
import com.foodwallet.server.domain.bookmark.repository.BookmarkRepository;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.OperationalInfo;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.assertj.core.api.Assertions;
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
}