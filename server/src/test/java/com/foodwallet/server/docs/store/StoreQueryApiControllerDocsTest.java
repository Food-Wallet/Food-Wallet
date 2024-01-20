package com.foodwallet.server.docs.store;

import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.controller.store.StoreQueryApiController;
import com.foodwallet.server.api.service.store.StoreQueryService;
import com.foodwallet.server.api.service.store.response.StoreDetailResponse;
import com.foodwallet.server.docs.RestDocsSupport;
import com.foodwallet.server.domain.menu.SellingStatus;
import com.foodwallet.server.domain.menu.repository.response.MenuResponse;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreQueryApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/stores";

    private final StoreQueryService storeQueryService = mock(StoreQueryService.class);

    @Override
    protected Object initController() {
        return new StoreQueryApiController(storeQueryService);
    }

    @DisplayName("매장 조회 API")
    @Test
    void searchStores() throws Exception {
        StoreResponse store = StoreResponse.builder()
            .storeId(1L)
            .type(StoreType.CHICKEN)
            .name("나리닭강정")
            .address("서울 중구 세종대로 110")
            .openTime("오전 11:00 ~ 오후 8:00")
            .storeImage("s3-store-img-url")
            .avgRate(5.0)
            .build();
        PageRequest pageable = PageRequest.of(0, 10);
        SliceImpl<StoreResponse> slice = new SliceImpl<>(List.of(store), pageable, false);

        SliceResponse<StoreResponse> response = SliceResponse.of(slice);

        given(storeQueryService.searchStores(anyString(), anyString(), any(Pageable.class)))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .param("type", "CHICKEN")
                    .param("query", "닭강정")
                    .param("page", "1")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-stores",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                queryParameters(
                    parameterWithName("type")
                        .optional()
                        .description("검색할 매장 타입"),
                    parameterWithName("query")
                        .optional()
                        .description("검색할 매장명"),
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

    @DisplayName("매장 상세 조회 API")
    @Test
    void searchStore() throws Exception {
        MenuResponse menu1 = MenuResponse.builder()
            .menuId(1L)
            .name("간장닭강정")
            .description("우리 매장 시그니처 메뉴입니다!")
            .price(8000)
            .status(SellingStatus.SELLING)
            .menuImage("s3-store-menu-img-url")
            .build();
        MenuResponse menu2 = MenuResponse.builder()
            .menuId(2L)
            .name("양념닭강정")
            .description(null)
            .price(9000)
            .status(SellingStatus.HOLD)
            .menuImage("s3-store-menu-img-url")
            .build();
        StoreDetailResponse response = StoreDetailResponse.builder()
            .storeId(1L)
            .type("치킨")
            .name("나리닭강정")
            .description("대한민국에서 1등 닭강정!")
            .storeImage("s3-store-img-url")
            .avgRate(5.0)
            .status("OPEN")
            .address("서울 중구 세종대로 110")
            .openTime("오전 11:00 ~ 오후 8:00")
            .menus(List.of(menu1, menu2))
            .build();

        given(storeQueryService.searchStore(anyLong()))
            .willReturn(response);

        mockMvc.perform(
                get(BASE_URL + "/{storeId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-store",
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
                    fieldWithPath("data.storeId").type(JsonFieldType.NUMBER)
                        .description("매장 식별키"),
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("매장 타입"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("매장명"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .optional()
                        .description("매장 설명"),
                    fieldWithPath("data.storeImage").type(JsonFieldType.STRING)
                        .optional()
                        .description("매장 이미지 URL"),
                    fieldWithPath("data.avgRate").type(JsonFieldType.NUMBER)
                        .description("매장 별점 평균"),
                    fieldWithPath("data.status").type(JsonFieldType.STRING)
                        .description("매장 운영 상태"),
                    fieldWithPath("data.address").type(JsonFieldType.STRING)
                        .optional()
                        .description("매장 운영 위치"),
                    fieldWithPath("data.openTime").type(JsonFieldType.STRING)
                        .optional()
                        .description("매장 운영 시간"),
                    fieldWithPath("data.menus").type(JsonFieldType.ARRAY)
                        .description("메뉴 목록"),
                    fieldWithPath("data.menus[].menuId").type(JsonFieldType.NUMBER)
                        .description("메뉴 식별키"),
                    fieldWithPath("data.menus[].name").type(JsonFieldType.STRING)
                        .description("메뉴명"),
                    fieldWithPath("data.menus[].description").type(JsonFieldType.STRING)
                        .optional()
                        .description("메뉴 설명"),
                    fieldWithPath("data.menus[].price").type(JsonFieldType.NUMBER)
                        .description("메뉴 가격"),
                    fieldWithPath("data.menus[].status").type(JsonFieldType.STRING)
                        .description("메뉴 판매 상태"),
                    fieldWithPath("data.menus[].menuImage").type(JsonFieldType.STRING)
                        .optional()
                        .description("메뉴 이미지 URL")
                )
            ));
    }
}
