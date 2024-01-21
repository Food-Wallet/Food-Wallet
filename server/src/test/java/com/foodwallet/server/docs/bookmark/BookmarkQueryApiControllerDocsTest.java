package com.foodwallet.server.docs.bookmark;

import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.controller.bookmark.BookmarkQueryApiController;
import com.foodwallet.server.api.service.bookmark.BookmarkQueryService;
import com.foodwallet.server.docs.RestDocsSupport;
import com.foodwallet.server.domain.bookmark.repository.response.BookmarkResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.foodwallet.server.domain.store.StoreStatus.OPEN;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookmarkQueryApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/bookmarks";
    private final BookmarkQueryService bookmarkQueryService = mock(BookmarkQueryService.class);

    @Override
    protected Object initController() {
        return new BookmarkQueryApiController(bookmarkQueryService);
    }

    @DisplayName("나의 즐겨찾기 매장 조회 API")
    @Test
    void searchStores() throws Exception {
        BookmarkResponse bookmark = BookmarkResponse.builder()
            .storeId(1L)
            .status(OPEN)
            .type(CHICKEN)
            .storeName("나리닭강정")
            .avgRate(5.0)
            .reviewCount(100)
            .storeImageUrl("s3-store-image-url")
            .build();
        PageRequest pageable = PageRequest.of(0, 10);
        SliceImpl<BookmarkResponse> slice = new SliceImpl<>(List.of(bookmark), pageable, false);

        SliceResponse<BookmarkResponse> response = SliceResponse.of(slice);

        given(bookmarkQueryService.searchBookmarks(anyString(), any(Pageable.class)))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL)
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
                    fieldWithPath("data.content[].status").type(JsonFieldType.STRING)
                        .description("매장 운영 상태"),
                    fieldWithPath("data.content[].type").type(JsonFieldType.STRING)
                        .description("매장 타입"),
                    fieldWithPath("data.content[].storeName").type(JsonFieldType.STRING)
                        .description("매장명"),
                    fieldWithPath("data.content[].avgRate").type(JsonFieldType.NUMBER)
                        .description("매장 리뷰 별점 평균"),
                    fieldWithPath("data.content[].reviewCount").type(JsonFieldType.NUMBER)
                        .description("매장 리뷰 갯수"),
                    fieldWithPath("data.content[].storeImageUrl").type(JsonFieldType.STRING)
                        .description("매장 이미지 URL"),
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
