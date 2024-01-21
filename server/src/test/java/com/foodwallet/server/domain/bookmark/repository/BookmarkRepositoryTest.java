package com.foodwallet.server.domain.bookmark.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.bookmark.Bookmark;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.foodwallet.server.domain.member.MemberRole.USER;
import static com.foodwallet.server.domain.store.StoreStatus.CLOSE;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.assertj.core.api.Assertions.*;

class BookmarkRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("회원 식별키와 매장 식별키로 즐겨찾기 조회시 일치하는 데이터가 없으면 예외가 발생한다.")
    @Test
    void findByIdWithoutTuple() {
        //given //when //then
        assertThatThrownBy(() -> bookmarkRepository.findById(1L, 1L))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("존재하지 않는 즐겨찾기입니다.");
    }

    @DisplayName("회원 식별키와 매장 식별키로 즐겨찾기를 조회한다.")
    @Test
    void findById() {
        //given
        Member member = createMember();
        Store store = createStore();
        Bookmark bookmark = createBookmark(member, store);

        //when
        Bookmark findBookmark = bookmarkRepository.findById(member.getId(), store.getId());

        //then
        assertThat(findBookmark).isNotNull();
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