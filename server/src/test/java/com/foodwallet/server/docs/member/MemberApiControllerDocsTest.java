package com.foodwallet.server.docs.member;

import com.foodwallet.server.api.controller.member.MemberApiController;
import com.foodwallet.server.api.controller.member.request.ConnectAccountRequest;
import com.foodwallet.server.api.controller.member.request.MatchAuthenticationNumberRequest;
import com.foodwallet.server.api.controller.member.request.MemberWithdrawalRequest;
import com.foodwallet.server.api.controller.member.request.PwdModifyRequest;
import com.foodwallet.server.api.service.member.AuthenticationService;
import com.foodwallet.server.api.service.member.MemberQueryService;
import com.foodwallet.server.api.service.member.MemberService;
import com.foodwallet.server.api.service.member.request.ConnectAccountServiceRequest;
import com.foodwallet.server.api.service.member.response.ConnectAccountResponse;
import com.foodwallet.server.api.service.member.response.MemberInfoResponse;
import com.foodwallet.server.api.service.member.response.MemberWithdrawalResponse;
import com.foodwallet.server.api.service.member.response.PwdModifyResponse;
import com.foodwallet.server.docs.RestDocsSupport;
import com.foodwallet.server.domain.member.MemberRole;
import com.foodwallet.server.security.SecurityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/members";

    private final AuthenticationService authenticationService = mock(AuthenticationService.class);
    private final MemberService memberService = mock(MemberService.class);
    private final MemberQueryService memberQueryService = mock(MemberQueryService.class);

    @Override
    protected Object initController() {
        return new MemberApiController(authenticationService, memberService, memberQueryService);
    }

    @DisplayName("계좌 연결 인증 번호 발급 API")
    @Test
    void connectAccount() throws Exception {
        ConnectAccountRequest request = ConnectAccountRequest.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .accountPwd("1234")
            .build();

        ConnectAccountResponse response = ConnectAccountResponse.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .build();

        given(SecurityUtils.getCurrentEmail())
            .willReturn("dong82@naver.com");

        given(authenticationService.connectAccount(anyString(), any(ConnectAccountServiceRequest.class)))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/account")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("connect-account",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                requestFields(
                    fieldWithPath("bankCode").type(JsonFieldType.STRING)
                        .description("은행 코드"),
                    fieldWithPath("accountNumber").type(JsonFieldType.STRING)
                        .description("계좌 번호"),
                    fieldWithPath("accountPwd").type(JsonFieldType.STRING)
                        .description("계좌 비밀번호")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.bankCode").type(JsonFieldType.STRING)
                        .description("등록된 은행 코드"),
                    fieldWithPath("data.accountNumber").type(JsonFieldType.STRING)
                        .description("등록된 계좌 번호")
                )
            ));
    }

    @DisplayName("인증 번호 확인 API")
    @Test
    void matchAuthenticationNumber() throws Exception {
        MatchAuthenticationNumberRequest request = MatchAuthenticationNumberRequest.builder()
            .authenticationNumber("1234")
            .build();

        ConnectAccountResponse response = ConnectAccountResponse.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .build();

        given(SecurityUtils.getCurrentEmail())
            .willReturn("dong82@naver.com");

        given(authenticationService.matchAuthenticationNumber(anyString(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/account/match")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("match-authentication-number",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                requestFields(
                    fieldWithPath("authenticationNumber").type(JsonFieldType.STRING)
                        .description("인증 번호")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.bankCode").type(JsonFieldType.STRING)
                        .description("등록된 은행 코드"),
                    fieldWithPath("data.accountNumber").type(JsonFieldType.STRING)
                        .description("등록된 계좌 번호")
                )
            ));
    }

    @DisplayName("비밀번호 변경 API")
    @Test
    void modifyPwd() throws Exception {
        PwdModifyRequest request = PwdModifyRequest.builder()
            .currentPwd("dong1234!")
            .newPwd("dong5678@")
            .build();

        PwdModifyResponse response = PwdModifyResponse.builder()
            .email("dong82@naver.com")
            .build();

        given(SecurityUtils.getCurrentEmail())
            .willReturn("dong82@naver.com");

        given(memberService.modifyPwd(anyString(), anyString(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                patch(BASE_URL + "/pwd")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-pwd",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                requestFields(
                    fieldWithPath("currentPwd").type(JsonFieldType.STRING)
                        .description("현재 비밀번호"),
                    fieldWithPath("newPwd").type(JsonFieldType.STRING)
                        .description("새로운 비밀번호")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("비밀번호 변경된 계정의 이메일")
                )
            ));
    }

    @DisplayName("회원 탈퇴 API")
    @Test
    void withdrawal() throws Exception {
        MemberWithdrawalRequest request = MemberWithdrawalRequest.builder()
            .pwd("dong1234!")
            .build();

        MemberWithdrawalResponse response = MemberWithdrawalResponse.builder()
            .email("dong82@naver.com")
            .name("동팔이")
            .withdrawalDateTime(LocalDateTime.of(2024, 1, 16, 18, 0))
            .build();

        given(SecurityUtils.getCurrentEmail())
            .willReturn("dong82@naver.com");

        given(memberService.removeMember(anyString(), anyString()))
            .willReturn(response);

        mockMvc.perform(
                patch(BASE_URL + "/withdrawal")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("withdrawal",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                requestFields(
                    fieldWithPath("pwd").type(JsonFieldType.STRING)
                        .description("현재 비밀번호")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("탈퇴한 계정 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("탈퇴한 계정 회원명"),
                    fieldWithPath("data.withdrawalDateTime").type(JsonFieldType.ARRAY)
                        .description("탈퇴 일시")
                )
            ));
    }

    @DisplayName("회원 정보 조회 API")
    @Test
    void searchMemberInfo() throws Exception {
        MemberInfoResponse response = MemberInfoResponse.builder()
            .email("dong82@naver.com")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(MemberRole.USER)
            .bankCode("088")
            .accountNumber("110111222222")
            .build();

        given(SecurityUtils.getCurrentEmail())
            .willReturn("dong82@naver.com");

        given(memberQueryService.searchMemberInfo(anyString()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-member-info",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("계정 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("회원명"),
                    fieldWithPath("data.birthYear").type(JsonFieldType.NUMBER)
                        .description("회원 출생연도"),
                    fieldWithPath("data.gender").type(JsonFieldType.STRING)
                        .description("회원 성별"),
                    fieldWithPath("data.role").type(JsonFieldType.STRING)
                        .description("회원 구분"),
                    fieldWithPath("data.account.bankCode").type(JsonFieldType.STRING)
                        .description("회원 계좌 은행 코드"),
                    fieldWithPath("data.account.accountNumber").type(JsonFieldType.STRING)
                        .description("회원 계좌 번호")
                )
            ));
    }
}
