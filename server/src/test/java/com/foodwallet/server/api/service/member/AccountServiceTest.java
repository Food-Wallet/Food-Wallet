package com.foodwallet.server.api.service.member;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.security.TokenInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.*;

class AccountServiceTest extends IntegrationTestSupport {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("로그인시 입력 받은 비밀번호가 틀리면 예외가 발생한다.")
    @Test
    void loginWithNotEqualsPassword() {
        //given
        Member member = createMember();

        //when //then
        assertThatThrownBy(() -> accountService.login("dong82@naver.com", "dong1234@", "fcmToken"))
            .isInstanceOf(BadCredentialsException.class)
            .hasMessage("자격 증명에 실패하였습니다.");
    }

    @DisplayName("이메일, 비밀번호, FCM 토큰 정보를 입력 받아 JWT를 생성한다. 회원의 토큰 정보는 수정된다.")
    @Test
    void login() {
        //given
        Member member = createMember();

        //when
        TokenInfo tokenInfo = accountService.login("dong82@naver.com", "dong1234!", "fcmToken");

        //then
        Member findMember = memberRepository.findByEmail("dong82@naver.com");
        assertThat(findMember)
            .extracting("name", "token.fcmToken", "token.refreshToken")
            .contains("동팔이", "fcmToken", tokenInfo.getRefreshToken());
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