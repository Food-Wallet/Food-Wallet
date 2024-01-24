package com.foodwallet.server.api.service.member;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.member.response.CheckEmailDuplicationResponse;
import com.foodwallet.server.api.service.member.response.MemberInfoResponse;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class MemberQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberQueryService memberQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일을 입력 받아 이메일 중복 여부를 확인한다.")
    @Test
    void checkEmailDuplicationWithDuplicateEmail() {
        //given
        Member member = createMember();

        //when
        CheckEmailDuplicationResponse response = memberQueryService.checkEmailDuplication("dong82@naver.com");

        //then
        assertThat(response.isDuplicated()).isTrue();
    }

    @DisplayName("이메일을 입력 받아 이메일 중복 여부를 확인한다.")
    @Test
    void checkEmailDuplication() {
        //given

        //when
        CheckEmailDuplicationResponse response = memberQueryService.checkEmailDuplication("dong82@naver.com");

        //then
        assertThat(response.isDuplicated()).isFalse();
    }

    @DisplayName("회원 이메일을 입력 받아 회원 정보를 조회한다.")
    @Test
    void searchMemberInfo() {
        //given
        Member member = createMember();

        //when
        MemberInfoResponse response = memberQueryService.searchMemberInfo("dong82@naver.com");

        //then
        assertThat(response)
            .extracting("email", "name", "birthYear", "gender", "role", "account.bankCode", "account.accountNumber")
            .contains("dong82@naver.com", "동팔이", 2015, "F", MemberRole.USER, null, null);
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