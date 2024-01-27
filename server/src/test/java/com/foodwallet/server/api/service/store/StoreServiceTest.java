package com.foodwallet.server.api.service.store;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreModifyInfoServiceRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.api.service.store.response.StoreModifyInfoResponse;
import com.foodwallet.server.common.exception.AuthenticationException;
import com.foodwallet.server.domain.member.Account;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.domain.store.Store;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static com.foodwallet.server.domain.member.MemberRole.BUSINESS;
import static com.foodwallet.server.domain.member.MemberRole.USER;
import static org.assertj.core.api.Assertions.*;

class StoreServiceTest extends IntegrationTestSupport {

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("신규 매장 등록시 사업자 회원이 아니라면 예외가 발생한다.")
    @Test
    void createStoreWithNotBusinessMember() {
        //given
        Account account = createAccount();
        Member member = createMember("dong82@naver.com", USER, account);
        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내 1위 닭강정 맛집!")
            .image(null)
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.createStore("dong82@naver.com", request))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("사업자 회원만 매장을 등록할 수 있습니다.");
    }

    @DisplayName("신규 매장 등록시 회원의 계좌 정보가 존재하지 않으면 예외가 발생한다.")
    @Test
    void createStoreWithoutAccountInfo() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내 1위 닭강정 맛집!")
            .image(null)
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.createStore("dong82@naver.com", request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("계좌 정보가 존재하지 않습니다.");
    }

    @DisplayName("사업자 회원의 이메일과 매장 정보를 입력 받아 신규 매장을 등록한다.")
    @Test
    void createStore() throws IOException {
        //given
        Account account = createAccount();
        Member member = createMember("dong82@naver.com", BUSINESS, account);
        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내 1위 닭강정 맛집!")
            .image(null)
            .build();

        //when
        StoreCreateResponse response = storeService.createStore("dong82@naver.com", request);

        //then
        assertThat(response)
            .extracting("storeType", "storeName", "storeDescription", "storeImage")
            .contains("치킨", "나리닭강정", "국내 1위 닭강정 맛집!", null);
    }

    @DisplayName("매장 정보 수정시 본인의 매장이 아니라면 예외가 발생한다.")
    @Test
    void modifyStoreInfoWithNotAuth() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        StoreModifyInfoServiceRequest request = StoreModifyInfoServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내에서 제일 잘나가는 닭강정!")
            .build();

        Member otherMember = createMember("do72@naver.com", BUSINESS, null);

        //when //then
        assertThatThrownBy(() -> storeService.modifyStoreInfo("do72@naver.com", store.getId(), request))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("매장 정보 수정시 매장이 운영중이라면 예외가 발생한다.")
    @Test
    void modifyStoreInfoWithOpen() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.OPEN);
        StoreModifyInfoServiceRequest request = StoreModifyInfoServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내에서 제일 잘나가는 닭강정!")
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.modifyStoreInfo("dong82@naver.com", store.getId(), request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("영업중에는 매장 정보를 수정할 수 없습니다.");
    }

    @DisplayName("회원의 이메일, 매장 식별키, 매장 정보를 입력 받아 매장 정보를 수정한다. ")
    @Test
    void modifyStoreInfo() {
        //given
        Member member = createMember("dong82@naver.com", BUSINESS, null);
        Store store = createStore(member, StoreStatus.CLOSE);
        StoreModifyInfoServiceRequest request = StoreModifyInfoServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .description("국내에서 제일 잘나가는 닭강정!")
            .build();

        //when
        StoreModifyInfoResponse response = storeService.modifyStoreInfo("dong82@naver.com", store.getId(), request);

        //then
        assertThat(response)
            .extracting("storeId", "storeType", "storeName", "storeDescription")
            .contains(store.getId(), "치킨", "나리닭강정", "국내에서 제일 잘나가는 닭강정!");

        Store findStore = storeRepository.findById(store.getId());
        assertThat(findStore)
            .extracting("type", "name", "description")
            .contains(StoreType.CHICKEN, "나리닭강정", "국내에서 제일 잘나가는 닭강정!");
    }

    public Account createAccount() {
        return Account.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .accountPwd("1234")
            .build();
    }

    public Member createMember(String email, MemberRole role, Account account) {
        Member member = Member.builder()
            .email(email)
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(role)
            .account(account)
            .build();
        return memberRepository.save(member);
    }

    private Store createStore(Member member, StoreStatus status) {
        Store store = Store.builder()
            .status(status)
            .type(StoreType.CHICKEN)
            .name("나리닭강정")
            .description("국내 1등 닭강정 맛집!")
            .member(member)
            .build();
        return storeRepository.save(store);
    }
}