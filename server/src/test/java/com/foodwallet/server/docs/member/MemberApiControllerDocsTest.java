package com.foodwallet.server.docs.member;

import com.foodwallet.server.api.controller.member.MemberApiController;
import com.foodwallet.server.api.controller.member.request.CheckEmailDuplicationRequest;
import com.foodwallet.server.api.controller.member.request.MemberWithdrawalRequest;
import com.foodwallet.server.api.controller.member.request.PwdModifyRequest;
import com.foodwallet.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/members";

    @Override
    protected Object initController() {
        return new MemberApiController();
    }

    @DisplayName("비밀번호 변경 API")
    @Test
    void modifyPwd() throws Exception {
        PwdModifyRequest request = PwdModifyRequest.builder()
            .currentPwd("dong1234!")
            .newPwd("dong5678@")
            .build();

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
                    fieldWithPath("data").type(JsonFieldType.NULL)
                        .description("응답 데이터")
                )
            ));
    }

    @DisplayName("회원 탈퇴 API")
    @Test
    void withdrawal() throws Exception {
        MemberWithdrawalRequest request = MemberWithdrawalRequest.builder()
            .pwd("dong1234!")
            .build();

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
                    fieldWithPath("data.age").type(JsonFieldType.NUMBER)
                        .description("회원 나이"),
                    fieldWithPath("data.gender").type(JsonFieldType.STRING)
                        .description("회원 성별")
                )
            ));
    }
}
