package com.foodwallet.server.api.service.member;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.member.request.ConnectAccountServiceRequest;
import com.foodwallet.server.api.service.member.response.ConnectAccountResponse;
import com.foodwallet.server.domain.member.Account;
import com.foodwallet.server.domain.member.ConnectAccount;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.ConnectAccountRedisRepository;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class AuthenticationServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ConnectAccountRedisRepository connectAccountRedisRepository;

    @AfterEach
    void tearDown() {
        connectAccountRedisRepository.deleteAll();
    }

    @DisplayName("회원의 이메일과 계좌 정보를 입력 받아 인증 번호를 발급한다.")
    @Test
    void connectAccount() {
        //given
        Member member = createMember();
        ConnectAccountServiceRequest request = ConnectAccountServiceRequest.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .accountPwd("1234")
            .build();

        //when
        ConnectAccountResponse response = authenticationService.connectAccount("dong82@naver.com", request);

        //then
        Optional<ConnectAccount> findConnectAccount = connectAccountRedisRepository.findById("dong82@naver.com");
        assertThat(findConnectAccount).isPresent();
    }

    @DisplayName("인증 번호 확인시 입력 받은 이메일에 대해 발급된 인증 번호가 존재하지 않으면 예외가 발생한다.")
    @Test
    void matchAuthenticationNumberWithExpiredNumber() {
        //given
        Member member = createMember();

        //when //then
        assertThatThrownBy(() -> authenticationService.matchAuthenticationNumber("dong82@naver.com", "1234"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("유효 시간이 만료되었습니다.");
    }

    @DisplayName("인증 번호 확인시 입력 받은 인증 번호와 발급 받은 인증 번호가 다르면 예외가 발생한다.")
    @Test
    void matchAuthenticationNumberWithNotMatchNumber() {
        //given
        Member member = createMember();
        ConnectAccount connectAccount = createConnectAccount();

        //when //then
        assertThatThrownBy(() -> authenticationService.matchAuthenticationNumber("dong82@naver.com", "1111"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("인증 번호를 확인해주세요.");
    }

    @DisplayName("회원의 이메일과 인증 번호를 입력 받아 일치하면 계좌 정보를 등록한다.")
    @Test
    void matchAuthenticationNumber() {
        //given
        Member member = createMember();
        ConnectAccount connectAccount = createConnectAccount();

        //when
        ConnectAccountResponse response = authenticationService.matchAuthenticationNumber("dong82@naver.com", "1234");

        //then
        assertThat(response)
            .extracting("bankCode", "accountNumber")
            .contains("088", "110111222222");

        Member findMember = memberRepository.findByEmail("dong82@naver.com");
        assertThat(findMember.getAccount())
            .extracting("bankCode", "accountNumber", "accountPwd")
            .contains("088", "110111222222", "1234");

        Optional<ConnectAccount> findConnectAccount = connectAccountRedisRepository.findById("dong82@naver.com");
        assertThat(findConnectAccount).isEmpty();
    }

    private Member createMember() {
        Member member = Member.builder()
            .email("dong82@naver.com")
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.USER)
            .build();
        return memberRepository.save(member);
    }

    private ConnectAccount createConnectAccount() {
        ConnectAccount connectAccount = ConnectAccount.builder()
            .email("dong82@naver.com")
            .account(Account.builder()
                .bankCode("088")
                .accountNumber("110111222222")
                .accountPwd("1234")
                .build())
            .authenticationNumber("1234")
            .build();
        return connectAccountRedisRepository.save(connectAccount);
    }
}