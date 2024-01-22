package com.foodwallet.server.domain.member.repository;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class MemberQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일을 입력 받아 사용 여부를 조회한다.")
    @Test
    void isExistEmailWithExistEmail() {
        //given
        Member member = createMember();

        //when
        boolean isExistEmail = memberQueryRepository.existEmail("dong82@naver.com");

        //then
        assertThat(isExistEmail).isTrue();
    }

    @DisplayName("이메일을 입력 받아 사용 여부를 조회한다.")
    @Test
    void isExistEmail2() {
        //given

        //when
        boolean isExistEmail = memberQueryRepository.existEmail("dong82@naver.com");

        //then
        assertThat(isExistEmail).isFalse();
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