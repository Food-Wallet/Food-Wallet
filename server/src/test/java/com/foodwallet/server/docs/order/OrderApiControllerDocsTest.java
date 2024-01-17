package com.foodwallet.server.docs.order;

import com.foodwallet.server.api.controller.menu.request.MenuModifyStatusRequest;
import com.foodwallet.server.api.controller.order.OrderApiController;
import com.foodwallet.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/baskets/{basketId}/orders";

    @Override
    protected Object initController() {
        return new OrderApiController();
    }

    @DisplayName("주문 생성 API")
    @Test
    void createOrder() throws Exception {
        mockMvc.perform(
                post(BASE_URL, 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("create-order",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                pathParameters(
                    parameterWithName("basketId")
                        .description("장바구니 식별키")
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
                    fieldWithPath("data.orderId").type(JsonFieldType.NUMBER)
                        .description("주문 식별키"),
                    fieldWithPath("data.totalPrice").type(JsonFieldType.NUMBER)
                        .description("총 주문 금액"),
                    fieldWithPath("data.orderStatus").type(JsonFieldType.STRING)
                        .description("주문 상태"),
                    fieldWithPath("data.orderDateTime").type(JsonFieldType.ARRAY)
                        .description("주문 일시"),
                    fieldWithPath("data.orderMenus").type(JsonFieldType.ARRAY)
                        .description("주문한 메뉴 목록"),
                    fieldWithPath("data.orderMenus[].menuName").type(JsonFieldType.STRING)
                        .description("메뉴명"),
                    fieldWithPath("data.orderMenus[].menuImage").type(JsonFieldType.STRING)
                        .description("메뉴 이미지 URL"),
                    fieldWithPath("data.orderMenus[].orderPrice").type(JsonFieldType.NUMBER)
                        .description("주문 금액"),
                    fieldWithPath("data.orderMenus[].count").type(JsonFieldType.NUMBER)
                        .description("주문 수량")
                )
            ));
    }
}
