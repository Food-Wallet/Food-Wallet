package com.foodwallet.server.api.service.member;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.member.request.ConnectAccountServiceRequest;
import com.foodwallet.server.api.service.member.response.ConnectAccountResponse;
import com.foodwallet.server.domain.member.ConnectAccount;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.ConnectAccountRedisRepository;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

class AuthenticationServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ConnectAccountRedisRepository memberRedisRepository;

    @AfterEach
    void tearDown() {
        memberRedisRepository.deleteAll();
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
        Optional<ConnectAccount> findConnectAccount = memberRedisRepository.findById("dong82@naver.com");
        Assertions.assertThat(findConnectAccount).isPresent();
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
}