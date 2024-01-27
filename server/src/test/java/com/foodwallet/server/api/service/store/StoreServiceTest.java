package com.foodwallet.server.api.service.store;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.common.exception.AuthenticationException;
import com.foodwallet.server.domain.member.Account;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.MemberRepository;
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

    @DisplayName("신규 매장 등록시 사업자 회원이 아니라면 예외가 발생한다.")
    @Test
    void createStoreWithNotBusinessMember() {
        //given
        Account account = createAccount();
        Member member = createMember(USER, account);
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
        Member member = createMember(BUSINESS, null);
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
        Member member = createMember(BUSINESS, account);
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

    public Account createAccount() {
        return Account.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .accountPwd("1234")
            .build();
    }

    public Member createMember(MemberRole role, Account account) {
        Member member = Member.builder()
            .email("dong82@naver.com")
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(role)
            .account(account)
            .build();
        return memberRepository.save(member);
    }

}