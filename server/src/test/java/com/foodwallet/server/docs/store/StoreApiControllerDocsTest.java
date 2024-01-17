package com.foodwallet.server.docs.store;

import com.foodwallet.server.api.controller.store.StoreApiController;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyRequest;
import com.foodwallet.server.api.controller.store.request.StoreOpenRequest;
import com.foodwallet.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/stores";

    @Override
    protected Object initController() {
        return new StoreApiController();
    }

    @DisplayName("매장 등록 API")
    @Test
    void createStore() throws Exception {
        StoreCreateRequest request = StoreCreateRequest.builder()
            .type("치킨")
            .name("나리닭강정")
            .build();

        mockMvc.perform(
                post(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-store",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                requestFields(
                    fieldWithPath("type").type(JsonFieldType.STRING)
                        .description("매장 타입"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("매장명"),
                    fieldWithPath("description").type(JsonFieldType.STRING)
                        .optional()
                        .description("매장 설명")
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
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("매장 타입"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("매장명"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .optional()
                        .description("매장 설명"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("매장 등록 일시")
                )
            ));
    }

    @DisplayName("매장 오픈 API")
    @Test
    void openStore() throws Exception {
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("서울 중구 세종대로 110")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .build();

        mockMvc.perform(
                patch(BASE_URL + "/{storeId}/open", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("open-store",
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
                requestFields(
                    fieldWithPath("address").type(JsonFieldType.STRING)
                        .description("현재 위치 도로명 주소"),
                    fieldWithPath("latitude").type(JsonFieldType.NUMBER)
                        .description("현재 위치 위도"),
                    fieldWithPath("longitude").type(JsonFieldType.NUMBER)
                        .description("현재 위치 경도")
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
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("매장 이름"),
                    fieldWithPath("data.address").type(JsonFieldType.STRING)
                        .description("매장 운영 주소"),
                    fieldWithPath("data.latitude").type(JsonFieldType.NUMBER)
                        .description("매장 운영 위도"),
                    fieldWithPath("data.longitude").type(JsonFieldType.NUMBER)
                        .description("매장 운영 경도"),
                    fieldWithPath("data.openDateTime").type(JsonFieldType.ARRAY)
                        .description("매장 오픈 시간")
                )
            ));
    }

    @DisplayName("매장 종료 API")
    @Test
    void closeStore() throws Exception {
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}/close", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("close-store",
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
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("매장 이름"),
                    fieldWithPath("data.address").type(JsonFieldType.STRING)
                        .description("매장 운영 주소"),
                    fieldWithPath("data.latitude").type(JsonFieldType.NUMBER)
                        .description("매장 운영 위도"),
                    fieldWithPath("data.longitude").type(JsonFieldType.NUMBER)
                        .description("매장 운영 경도"),
                    fieldWithPath("data.closedDateTime").type(JsonFieldType.ARRAY)
                        .description("매장 종료 시간")
                )
            ));
    }

    @DisplayName("매장 정보 수정 API")
    @Test
    void modifyStoreInfo() throws Exception {
        StoreModifyRequest request = StoreModifyRequest.builder()
            .type("치킨")
            .name("나리닭강정")
            .description("대한민국에서 1등 닭강정!")
            .build();

        mockMvc.perform(
                patch(BASE_URL + "/{storeId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-store-info",
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
                requestFields(
                    fieldWithPath("type").type(JsonFieldType.STRING)
                        .description("매장 타입"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("매장명"),
                    fieldWithPath("description").type(JsonFieldType.STRING)
                        .optional()
                        .description("매장 설명")
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
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("매장 타입"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("매장명"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .optional()
                        .description("매장 설명"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("매장 정보 수정 일시")
                )
            ));
    }

    @DisplayName("매장 삭제 API")
    @Test
    void removeStore() throws Exception {
        mockMvc.perform(
                delete(BASE_URL + "/{storeId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-store",
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
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("매장 타입"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("매장명"),
                    fieldWithPath("data.removedDateTime").type(JsonFieldType.ARRAY)
                        .description("매장 삭제 일시")
                )
            ));
    }

    @DisplayName("매장 조회 API")
    @Test
    void searchStores() throws Exception {
        mockMvc.perform(
                get(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .param("type", "치킨")
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

    @DisplayName("매장 이미지 수정 API")
    @Test
    void modifyStoreImage() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
            "image",
            "my-store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        mockMvc.perform(
                multipart(BASE_URL + "/{storeId}/image", 1)
                    .file(image)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-store-image",
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
                requestParts(
                    partWithName("image")
                        .description("첨부파일")
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
                    fieldWithPath("data.type").type(JsonFieldType.STRING)
                        .description("매장 타입"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("매장명"),
                    fieldWithPath("data.imageModifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("매장 이미지 수정 일시")
                )
            ));
    }
}
