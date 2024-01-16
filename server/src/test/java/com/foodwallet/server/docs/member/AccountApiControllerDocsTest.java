package com.foodwallet.server.docs.member;

import com.foodwallet.server.api.controller.member.AccountApiController;
import com.foodwallet.server.api.controller.member.request.MemberCreateRequest;
import com.foodwallet.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

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

    @Override
    protected Object initController() {
        return new AccountApiController();
    }

    @DisplayName("회원 가입 API")
    @Test
    void signup() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("동팔이")
            .age(10)
            .gender("M")
            .build();

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
                    fieldWithPath("age").type(JsonFieldType.NUMBER)
                        .description("나이"),
                    fieldWithPath("gender").type(JsonFieldType.STRING)
                        .description("성별")
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
}