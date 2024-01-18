package com.foodwallet.server.api.service.store;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreModifyServiceRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.api.service.store.response.StoreModifyResponse;
import com.foodwallet.server.domain.member.Account;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.foodwallet.server.domain.member.MemberRole.*;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.assertj.core.api.Assertions.*;

class StoreServiceTest extends IntegrationTestSupport {

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("신규 매장을 생성시 사업자 회원이 아니라면 예외가 발생한다.")
    @Test
    void createStoreNotBusinessMember() {
        //given
        Account account = createAccount();
        Member member = createMember(USER, account, "dong82@naver.com");

        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.createStore(member.getEmail(), request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("사업자 회원만 매장을 등록할 수 있습니다.");
    }

    @DisplayName("신규 매장을 생성시 사업자 회원의 계좌 정보가 없으면 예외가 발생한다.")
    @Test
    void createStoreWithoutAccount() {
        //given
        Member member = createMember(BUSINESS, null, "dong82@naver.com");

        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.createStore(member.getEmail(), request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("매장을 등록하기 위해서 계좌를 등록해야 합니다.");
    }

    @DisplayName("사업자 회원 이메일과 매장 정보를 입력 받아 신규 매장을 생성한다.")
    @Test
    void createStore() {
        //given
        Account account = createAccount();
        Member member = createMember(BUSINESS, account, "dong82@naver.com");

        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .build();

        //when
        StoreCreateResponse response = storeService.createStore(member.getEmail(), request);

        //then
        assertThat(response)
            .extracting("type", "name")
            .contains("치킨", "나리닭강정");
    }

    @DisplayName("매장 정보 수정시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void modifyStoreInfoWithoutAuth() {
        //given
        Account account = createAccount();
        Member member1 = createMember(BUSINESS, account, "dong82@naver.com");
        Store store = createStore(member1);

        Member member2 = createMember(BUSINESS, account, "do72@naver.com");

        StoreModifyServiceRequest request = StoreModifyServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내 1등 닭강정")
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.modifyStoreInfo(member2.getEmail(), store.getId(), request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("사업자 회원 이메일, 매장 식별키, 매장 정보를 입력 받아 매장 정보를 수정한다.")
    @Test
    void modifyStoreInfo() {
        //given
        Account account = createAccount();
        Member member = createMember(BUSINESS, account, "dong82@naver.com");
        Store store = createStore(member);

        StoreModifyServiceRequest request = StoreModifyServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내 1등 닭강정")
            .build();

        //when
        StoreModifyResponse response = storeService.modifyStoreInfo(member.getEmail(), store.getId(), request);

        //then
        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore)
            .extracting("type", "name", "description")
            .contains(CHICKEN, "나리닭강정", "국내 1등 닭강정");
    }

    private Account createAccount() {
        return Account.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .accountPwd("1234")
            .build();
    }

    private Member createMember(MemberRole role, Account account, String email) {
        Member member = Member.builder()
            .email(email)
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .age(10)
            .gender("F")
            .role(role)
            .account(account)
            .token(null)
            .build();
        return memberRepository.save(member);
    }

    private Store createStore(Member member) {
        Store store = Store.builder()
            .status(StoreStatus.CLOSE)
            .type(CHICKEN)
            .name("나리닭강정")
            .member(member)
            .build();
        return storeRepository.save(store);
    }
}