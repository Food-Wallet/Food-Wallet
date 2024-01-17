package com.foodwallet.server.docs.basket;

import com.foodwallet.server.api.controller.basket.BasketApiController;
import com.foodwallet.server.api.controller.basket.request.BasketCreateRequest;
import com.foodwallet.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BasketApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/baskets";

    @Override
    protected Object initController() {
        return new BasketApiController();
    }

    @DisplayName("메뉴 담기 API")
    @Test
    void createBasket() throws Exception {
        BasketCreateRequest request = BasketCreateRequest.builder()
            .menuId(1L)
            .count(2)
            .build();

        mockMvc.perform(
                post(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("create-basket",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                requestFields(
                    fieldWithPath("menuId").type(JsonFieldType.NUMBER)
                        .description("메뉴 식별키"),
                    fieldWithPath("count").type(JsonFieldType.NUMBER)
                        .description("메뉴 담을 갯수")
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
                    fieldWithPath("data.menuName").type(JsonFieldType.STRING)
                        .description("담은 메뉴명"),
                    fieldWithPath("data.count").type(JsonFieldType.NUMBER)
                        .description("담은 메뉴 갯수"),
                    fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER)
                        .description("담은 메뉴 총 금액")
                )
            ));
    }
}
