package com.foodwallet.server.api.service.member;

import com.foodwallet.server.IntegrationTestSupport;
import com.foodwallet.server.api.service.member.request.MemberCreateServiceRequest;
import com.foodwallet.server.api.service.member.response.MemberCreateResponse;
import com.foodwallet.server.api.service.member.response.PwdModifyResponse;
import com.foodwallet.server.common.exception.AuthenticationException;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("신규 회원 등록시 입력 받은 이메일을 사용하는 회원이 존재하면 예외가 발생한다.")
    @Test
    void createMemberWithDuplicateEmail() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 22);

        Member member = createMember("dong82@naver.com");
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.USER)
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.createMember(request, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 가입된 이메일 주소입니다.");
    }

    @DisplayName("신규 회원 등록시 입력 받은 이메일의 형식이 맞지 않으면 예외가 발생한다.")
    @Test
    void createMemberWithNotMatchEmailPattern() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 22);
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("dong82@naver")
            .pwd("dong1234")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.USER)
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.createMember(request, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이메일을 올바르게 입력해주세요.");
    }

    @DisplayName("신규 회원 등록시 입력 받은 비밀번호의 길이가 8자 미만이라면 예외가 발생한다.")
    @Test
    void createMemberWithPasswordLessThan() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 22);
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong12!")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.USER)
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.createMember(request, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호의 길이는 8자 이상 20자 이하입니다.");
    }

    @DisplayName("신규 회원 등록시 입력 받은 비밀번호의 길이가 20자를 초과하면 예외가 발생한다.")
    @Test
    void createMemberWithPasswordGreaterThan() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 22);
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234567890123456!")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.USER)
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.createMember(request, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호의 길이는 8자 이상 20자 이하입니다.");
    }

    @DisplayName("신규 회원 등록시 입력 받은 비밀번호에 숫자, 영문, 특수문자가 한개 이상 포함되어 있지 않으면 예외가 발생한다.")
    @Test
    void createMemberWithNotMatchPasswordPattern() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 22);
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.USER)
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.createMember(request, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호를 올바르게 입력해주세요.");
    }

    @DisplayName("신규 회원 등록시 입력 받은 회원의 이름에 한글 이외의 문자가 포함되어 있으면 예외가 발생한다.")
    @Test
    void createMemberWithNotKoreanContainsName() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 22);
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("dong82")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.USER)
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.createMember(request, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이름을 올바르게 입력해주세요.");
    }

    @DisplayName("신규 회원 등록시 입력 받은 출생연도가 미래인 경우 예외가 발생한다.")
    @Test
    void createMemberWithFutureYear() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 22);
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("동팔이")
            .birthYear(2025)
            .gender("F")
            .role(MemberRole.USER)
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.createMember(request, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("출생연도를 올바르게 입력해주세요.");
    }

    @DisplayName("신규 회원 등록시 입력 받은 성별이 M, F이 아니라면 예외가 발생한다.")
    @Test
    void createMemberWithNotSupportGender() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 22);
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("동팔이")
            .birthYear(2015)
            .gender("A")
            .role(MemberRole.USER)
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.createMember(request, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("성별을 올바르게 입력해주세요.");
    }


    @DisplayName("회원 정보를 입력 받아 신규 회원을 등록한다.")
    @Test
    void createMember() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 22);
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.USER)
            .build();

        //when
        MemberCreateResponse response = memberService.createMember(request, currentDate);

        //then
        assertThat(response)
            .extracting("email", "name")
            .contains("dong82@naver.com", "동팔이");
    }

    @DisplayName("비밀번호 수정시 입력 받은 현재 비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void modifyPwdWithNotAuth() {
        //given
        Member member = createMember("dong82@naver.com");

        //when //then
        assertThatThrownBy(() -> memberService.modifyPwd("dong82@naver.com", "dong1111!", "dong8282@"))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("접근 권한이 없습니다.");
    }

    @DisplayName("비밀번호 수정시 입력 받은 비밀번호의 길이가 8자 미만이라면 예외가 발생한다.")
    @Test
    void modifyPwdWithPasswordLessThan() {
        //given
        Member member = createMember("dong82@naver.com");

        //when //then
        assertThatThrownBy(() -> memberService.modifyPwd("dong82@naver.com", "dong1234!", "dong82@"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호의 길이는 8자 이상 20자 이하입니다.");
    }

    @DisplayName("비밀번호 수정시 입력 받은 비밀번호의 길이가 20자를 초과하면 예외가 발생한다.")
    @Test
    void modifyPwdWithPasswordGreaterThan() {
        //given
        Member member = createMember("dong82@naver.com");

        //when //then
        assertThatThrownBy(() -> memberService.modifyPwd("dong82@naver.com", "dong1111!", "dong8282828282828282@"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호의 길이는 8자 이상 20자 이하입니다.");
    }

    @DisplayName("비밀번호 수정시 입력 받은 비밀번호에 숫자, 영문, 특수문자가 한개 이상 포함되어 있지 않으면 예외가 발생한다.")
    @Test
    void modifyPwdWithNotMatchPasswordPattern() {
        //given
        Member member = createMember("dong82@naver.com");

        //when //then
        assertThatThrownBy(() -> memberService.modifyPwd("dong82@naver.com", "dong1111!", "dong828282"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("비밀번호를 올바르게 입력해주세요.");
    }

    @DisplayName("회원의 이메일, 현재 비밀번호, 새로운 비밀번호를 입력 받아 비밀번호를 수정한다.")
    @Test
    void modifyPwd() {
        //given
        Member member = createMember("dong82@naver.com");

        //when
        PwdModifyResponse response = memberService.modifyPwd("dong82@naver.com", "dong1234!", "dong8282@");

        //then
        Member findMember = memberRepository.findByEmail("dong82@naver.com");
        boolean isMatches = passwordEncoder.matches("dong8282@", findMember.getPwd());
        assertThat(isMatches).isTrue();
    }

    private Member createMember(String email) {
        Member member = Member.builder()
            .email(email)
            .pwd(passwordEncoder.encode("dong1234!"))
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.USER)
            .build();
        return memberRepository.save(member);
    }
}