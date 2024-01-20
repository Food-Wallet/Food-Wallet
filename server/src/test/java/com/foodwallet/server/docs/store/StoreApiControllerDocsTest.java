package com.foodwallet.server.docs.store;

import com.foodwallet.server.api.FileStore;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.controller.store.StoreApiController;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyRequest;
import com.foodwallet.server.api.controller.store.request.StoreOpenRequest;
import com.foodwallet.server.api.service.store.StoreService;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreModifyServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreOpenServiceRequest;
import com.foodwallet.server.api.service.store.response.*;
import com.foodwallet.server.docs.RestDocsSupport;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import com.foodwallet.server.security.SecurityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;
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

    private final StoreService storeService = mock(StoreService.class);

    @Override
    protected Object initController() {
        return new StoreApiController(storeService);
    }

    @DisplayName("매장 등록 API")
    @Test
    void createStore() throws Exception {
        StoreCreateRequest request = StoreCreateRequest.builder()
            .type("치킨")
            .name("나리닭강정")
            .build();

        StoreCreateResponse response = StoreCreateResponse.builder()
            .type("치킨")
            .name("나리닭강정")
            .description(null)
            .createdDateTime(LocalDateTime.of(2024, 1, 16, 18, 0))
            .build();

        given(SecurityUtils.getCurrentEmail())
            .willReturn("dong82@naver.com");

        given(storeService.createStore(anyString(), any(StoreCreateServiceRequest.class)))
            .willReturn(response);

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
            .openTime("오전 10:00 ~ 오후 8:00")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .build();

        StoreOpenResponse response = StoreOpenResponse.builder()
            .name("나리닭강정")
            .address("서울 중구 세종대로 110")
            .openTime("오전 10:00 ~ 오후 8:00")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .openDateTime(LocalDateTime.of(2023, 1, 16, 14, 0))
            .build();

        given(SecurityUtils.getCurrentEmail())
            .willReturn("dong82@naver.com");

        given(storeService.openStore(anyString(), anyLong(), any(StoreOpenServiceRequest.class)))
            .willReturn(response);

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
                    fieldWithPath("openTime").type(JsonFieldType.STRING)
                        .description("매장 운영 시간"),
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
                    fieldWithPath("data.openTime").type(JsonFieldType.STRING)
                        .description("매장 운영 시간"),
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
        StoreCloseResponse response = StoreCloseResponse.builder()
            .name("나리닭강정")
            .address("서울 중구 세종대로 110")
            .openTime("오전 10:00 ~ 오후 8:00")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .closedDateTime(LocalDateTime.of(2023, 1, 16, 22, 0))
            .build();

        given(SecurityUtils.getCurrentEmail())
            .willReturn("dong82@naver.com");

        given(storeService.closeStore(anyString(), anyLong()))
            .willReturn(response);

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
                    fieldWithPath("data.openTime").type(JsonFieldType.STRING)
                        .description("매장 운영 시간"),
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

        StoreModifyResponse response = StoreModifyResponse.builder()
            .type("치킨")
            .name("나리닭강정")
            .description("대한민국에서 1등 닭강정!")
            .modifiedDateTime(LocalDateTime.of(2024, 1, 16, 9, 15))
            .build();

        given(SecurityUtils.getCurrentEmail())
            .willReturn("dong82@naver.com");

        given(storeService.modifyStoreInfo(anyString(), anyLong(), any(StoreModifyServiceRequest.class)))
            .willReturn(response);

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
        StoreRemoveResponse response = StoreRemoveResponse.builder()
            .type("치킨")
            .name("나리닭강정")
            .removedDateTime(LocalDateTime.of(2024, 1, 16, 20, 0))
            .build();

        given(storeService.removeStore(anyString(), anyLong()))
            .willReturn(response);

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

    @DisplayName("매장 이미지 수정 API")
    @Test
    void modifyStoreImage() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
            "image",
            "my-store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        StoreModifyImageResponse response = StoreModifyImageResponse.builder()
            .type("치킨")
            .name("나리닭강정")
            .imageModifiedDateTime(LocalDateTime.of(2024, 1, 17, 9, 0))
            .build();

        given(storeService.modifyStoreImage(anyString(), anyLong(), any(MultipartFile.class)))
            .willReturn(response);

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
                        .optional()
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
