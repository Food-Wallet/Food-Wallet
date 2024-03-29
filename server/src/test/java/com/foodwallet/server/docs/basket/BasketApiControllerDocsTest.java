package com.foodwallet.server.docs.basket;

import com.foodwallet.server.api.controller.basket.BasketApiController;
import com.foodwallet.server.api.controller.basket.request.BasketCreateRequest;
import com.foodwallet.server.api.controller.basket.request.BasketModifyRequest;
import com.foodwallet.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
            .andExpect(status().isCreated())
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

    @DisplayName("장바구니 조회 API")
    @Test
    void searchBasket() throws Exception {
        mockMvc.perform(
                get(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-basket",
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
                    fieldWithPath("data.basketId").type(JsonFieldType.NUMBER)
                        .description("장바구니 식별키"),
                    fieldWithPath("data.storeName").type(JsonFieldType.STRING)
                        .description("매장명"),
                    fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER)
                        .description("총 금액"),
                    fieldWithPath("data.menus").type(JsonFieldType.ARRAY)
                        .description("장바구니 담은 메뉴 목록"),
                    fieldWithPath("data.menus[].menuId").type(JsonFieldType.NUMBER)
                        .description("메뉴 식별키"),
                    fieldWithPath("data.menus[].menuName").type(JsonFieldType.STRING)
                        .description("메뉴명"),
                    fieldWithPath("data.menus[].menuImage").type(JsonFieldType.STRING)
                        .description("메뉴 이미지 URL"),
                    fieldWithPath("data.menus[].count").type(JsonFieldType.NUMBER)
                        .description("메뉴 갯수"),
                    fieldWithPath("data.menus[].totalPrice").type(JsonFieldType.NUMBER)
                        .description("메뉴 총 금액")
                )
            ));
    }

    @DisplayName("메뉴 갯수 수정 API")
    @Test
    void modifyBasket() throws Exception {
        BasketModifyRequest request = BasketModifyRequest.builder()
            .count(3)
            .build();

        mockMvc.perform(
                patch(BASE_URL + "/{basketMenuId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-basket",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                pathParameters(
                    parameterWithName("basketMenuId")
                        .description("장바구니 메뉴 식별키")
                ),
                requestFields(
                    fieldWithPath("count").type(JsonFieldType.NUMBER)
                        .description("수정할 메뉴 갯수")
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
                        .description("수정된 메뉴명"),
                    fieldWithPath("data.count").type(JsonFieldType.NUMBER)
                        .description("수정된 메뉴 갯수"),
                    fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER)
                        .description("수정된 메뉴 총 금액")
                )
            ));
    }
}
