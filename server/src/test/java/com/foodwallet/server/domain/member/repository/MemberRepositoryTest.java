package com.foodwallet.server.domain.member.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.foodwallet.server.domain.member.MemberRole.USER;
import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일로 회원을 조회한다.")
    @Test
    void findByEmail() {
        //given
        Member member = createMember();

        //when
        Optional<Member> findMember = memberRepository.findByEmail("dong82@naver.com");

        //then
        assertThat(findMember).isPresent();
    }

    private Member createMember() {
        Member member = Member.builder()
            .email("dong82@naver.com")
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .age(10)
            .gender("F")
            .role(USER)
            .account(null)
            .token(null)
            .build();
        return memberRepository.save(member);
    }
}