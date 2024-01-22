package com.foodwallet.server.domain.member.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;

import static com.foodwallet.server.domain.member.MemberRole.USER;
import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일로 회원 조회시 일치하는 데이터가 없으면 예외가 발생한다.")
    @Test
    void findByEmailWithoutTuple() {
        //given //when //then
        assertThatThrownBy(() -> memberRepository.findByEmail("no-reply@naver.com"))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("이메일로 회원을 조회한다.")
    @Test
    void findByEmail() {
        //given
        Member member = createMember();

        //when
        Member findMember = memberRepository.findByEmail("dong82@naver.com");

        //then
        assertThat(findMember)
            .extracting("email", "name", "age", "gender", "role")
            .contains("dong82@naver.com", "동팔이", 10, "F", USER);
    }

    private Member createMember() {
        Member member = Member.builder()
            .email("dong82@naver.com")
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(USER)
            .account(null)
            .token(null)
            .build();
        return memberRepository.save(member);
    }
}