package com.foodwallet.server.api.service.store;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.domain.member.Account;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import static com.foodwallet.server.domain.member.MemberRole.*;
import static org.assertj.core.api.Assertions.*;

class StoreServiceTest extends IntegrationTestSupport {

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("신규 매장을 생성시 사업자 회원이 아니라면 예외가 발생한다.")
    @Test
    void createStoreNotBusinessMember() {
        //given
        Account account = createAccount();
        Member member = createMember(USER, account);

        StoreCreateServiceRequest request = StoreCreateServiceRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .build();

        //when //then
        assertThatThrownBy(() -> storeService.createStore(member.getEmail(), request))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("사업자 회원만 매장을 등록할 수 있습니다.");
    }

    @DisplayName("신규 매장을 생성시 사업자 회원의 계좌 정보가 없으면 예외가 발생한다.")
    @Test
    void createStoreWithoutAccount() {
        //given
        Member member = createMember(BUSINESS, null);

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
        Member member = createMember(BUSINESS, account);

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

    private Member createMember(MemberRole role, Account account) {
        Member member = Member.builder()
            .email("dong82@naver.com")
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

    private Account createAccount() {
        return Account.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .accountPwd("1234")
            .build();
    }
}