package com.foodwallet.server.docs.member;

import com.foodwallet.server.api.controller.member.AccountApiController;
import com.foodwallet.server.api.controller.member.request.CheckEmailDuplicationRequest;
import com.foodwallet.server.api.controller.member.request.LoginRequest;
import com.foodwallet.server.api.controller.member.request.MemberCreateRequest;
import com.foodwallet.server.api.service.member.AccountService;
import com.foodwallet.server.api.service.member.MemberQueryService;
import com.foodwallet.server.api.service.member.MemberService;
import com.foodwallet.server.api.service.member.request.MemberCreateServiceRequest;
import com.foodwallet.server.api.service.member.response.CheckEmailDuplicationResponse;
import com.foodwallet.server.api.service.member.response.MemberCreateResponse;
import com.foodwallet.server.docs.RestDocsSupport;
import com.foodwallet.server.security.TokenInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.foodwallet.server.domain.member.MemberRole.USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/auth";
    private final MemberService memberService = mock(MemberService.class);
    private final MemberQueryService memberQueryService = mock(MemberQueryService.class);
    private final AccountService accountService = mock(AccountService.class);

    @Override
    protected Object initController() {
        return new AccountApiController(memberService, memberQueryService, accountService);
    }

    @DisplayName("회원 가입 API")
    @Test
    void signup() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(USER)
            .build();

        MemberCreateResponse response = MemberCreateResponse.builder()
            .email("dong82@naver.com")
            .name("동팔이")
            .signupDateTime(LocalDateTime.of(2024, 1, 16, 9, 0))
            .build();

        given(memberService.createMember(any(MemberCreateServiceRequest.class), any(LocalDate.class)))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/signup")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("signup",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("이메일"),
                    fieldWithPath("pwd").type(JsonFieldType.STRING)
                        .description("비밀번호"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("이름"),
                    fieldWithPath("birthYear").type(JsonFieldType.NUMBER)
                        .description("출생연도"),
                    fieldWithPath("gender").type(JsonFieldType.STRING)
                        .description("성별"),
                    fieldWithPath("role").type(JsonFieldType.STRING)
                        .description("회원 구분\nUSER(일반 회원), BUSINESS(사업자 회원), ADMIN(시스템 관리자 회원)")
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
                        .description("회원 가입된 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("회원 가입된 회원명"),
                    fieldWithPath("data.signupDateTime").type(JsonFieldType.ARRAY)
                        .description("회원 가입된 일시")
                )
            ));
    }

    @DisplayName("회원 로그인 API")
    @Test
    void login() throws Exception {
        LoginRequest request = LoginRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .fcmToken("fcm.token")
            .build();

        TokenInfo tokenInfo = TokenInfo.builder()
            .grantType("Bearer")
            .accessToken("jwt.access.token")
            .refreshToken("jwt.refresh.token")
            .build();

        given(accountService.login(anyString(), anyString(), anyString()))
            .willReturn(tokenInfo);

        mockMvc.perform(
                post(BASE_URL + "/login")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("login",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("이메일"),
                    fieldWithPath("pwd").type(JsonFieldType.STRING)
                        .description("비밀번호"),
                    fieldWithPath("fcmToken").type(JsonFieldType.STRING)
                        .description("FCM 토큰 정보")
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
                    fieldWithPath("data.grantType").type(JsonFieldType.STRING)
                        .description("인증 권한"),
                    fieldWithPath("data.accessToken").type(JsonFieldType.STRING)
                        .description("접근 토큰"),
                    fieldWithPath("data.refreshToken").type(JsonFieldType.STRING)
                        .description("재인증 토큰")
                )
            ));
    }

    @DisplayName("이메일 중복 체크 API")
    @Test
    void checkEmailDuplication() throws Exception {
        CheckEmailDuplicationRequest request = CheckEmailDuplicationRequest.builder()
            .email("dong82@naver.com")
            .build();

        CheckEmailDuplicationResponse response = CheckEmailDuplicationResponse.builder()
            .isDuplicated(true)
            .build();

        given(memberQueryService.checkEmailDuplication(anyString()))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/email")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("check-email-duplication",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("이메일")
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
                    fieldWithPath("data.duplicated").type(JsonFieldType.BOOLEAN)
                        .description("이메일 중복 여부")
                )
            ));
    }

    @DisplayName("로그아웃 API")
    @Test
    void logout() throws Exception {
        mockMvc.perform(
                post(BASE_URL + "/logout")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("logout",
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
                    fieldWithPath("data").type(JsonFieldType.NULL)
                        .description("응답 데이터")
                )
            ));
    }
}
