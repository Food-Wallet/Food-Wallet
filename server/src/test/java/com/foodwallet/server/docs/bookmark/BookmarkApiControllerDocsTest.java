package com.foodwallet.server.docs.bookmark;

import com.foodwallet.server.api.controller.bookmark.BookmarkApiController;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookmarkApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1";

    @Override
    protected Object initController() {
        return new BookmarkApiController();
    }

    @DisplayName("매장 즐겨찾기 등록 API")
    @Test
    void createBookmark() throws Exception {
        mockMvc.perform(
                post(BASE_URL + "/stores/{storeId}/bookmark", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("create-bookmark",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                pathParameters(
                    parameterWithName("storeId")
                        .description("매장 식별키")
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
                    fieldWithPath("data.storeName").type(JsonFieldType.STRING)
                        .description("즐겨찾기 등록한 매장명")
                )
            ));
    }

    @DisplayName("매장 즐겨찾기 취소 API")
    @Test
    void cancelBookmark() throws Exception {
        mockMvc.perform(
                delete(BASE_URL + "/stores/{storeId}/bookmark", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("cancel-bookmark",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                pathParameters(
                    parameterWithName("storeId")
                        .description("매장 식별키")
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
                    fieldWithPath("data.storeName").type(JsonFieldType.STRING)
                        .description("즐겨찾기 취소한 매장명")
                )
            ));
    }

    @DisplayName("나의 즐겨찾기 매장 조회 API")
    @Test
    void searchStores() throws Exception {
        mockMvc.perform(
                get(BASE_URL + "/bookmarks")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .param("page", "1")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-bookmarks",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                queryParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호")
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
                    fieldWithPath("data.content").type(JsonFieldType.ARRAY)
                        .description("매장 데이터"),
                    fieldWithPath("data.content[].storeId").type(JsonFieldType.NUMBER)
                        .description("매장 식별키"),
                    fieldWithPath("data.content[].type").type(JsonFieldType.STRING)
                        .description("매장 타입"),
                    fieldWithPath("data.content[].name").type(JsonFieldType.STRING)
                        .description("매장명"),
                    fieldWithPath("data.content[].address").type(JsonFieldType.STRING)
                        .description("매장 운영 주소"),
                    fieldWithPath("data.content[].openTime").type(JsonFieldType.STRING)
                        .description("매장 운영 시간"),
                    fieldWithPath("data.content[].storeImage").type(JsonFieldType.STRING)
                        .description("매장 썸네일 이미지 주소"),
                    fieldWithPath("data.content[].avgRate").type(JsonFieldType.NUMBER)
                        .description("매장 리뷰 별점 평균"),
                    fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER)
                        .description("현재 페이지 번호"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("데이터 요청 갯수"),
                    fieldWithPath("data.isFirst").type(JsonFieldType.BOOLEAN)
                        .description("첫 페이지 여부"),
                    fieldWithPath("data.isLast").type(JsonFieldType.BOOLEAN)
                        .description("마지막 페이지 여부")
                )
            ));
    }
}
