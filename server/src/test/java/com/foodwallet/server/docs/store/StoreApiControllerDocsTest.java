package com.foodwallet.server.docs.store;

import com.foodwallet.server.api.controller.store.StoreApiController;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import com.foodwallet.server.api.service.store.StoreService;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.response.StoreCreateResponse;
import com.foodwallet.server.docs.RestDocsSupport;
import com.foodwallet.server.security.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoreApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/stores";

    private final StoreService storeService = mock(StoreService.class);

    @Override
    protected Object initController() {
        return new StoreApiController(storeService);
    }

    @BeforeEach
    void setUp() {
        given(SecurityUtils.getCurrentEmail())
            .willReturn("dong82@naver.com");
    }

    @DisplayName("신규 매장 등록 API")
    @Test
    void createStore() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
            "storeImage",
            "store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        StoreCreateRequest request = StoreCreateRequest.builder()
            .storeType("CHICKEN")
            .storeName("나리닭강정")
            .storeDescription("국내 1등 닭강정 맛집!")
            .storeImage(image)
            .build();

        StoreCreateResponse response = StoreCreateResponse.builder()
            .storeId(1L)
            .storeType("치킨")
            .storeName("나리닭강정")
            .storeDescription("국내 1등 닭강정 맛집!")
            .storeImage("s3-store-image-url")
            .createdDateTime(LocalDateTime.of(2024, 1, 27, 17, 30))
            .build();

        given(storeService.createStore(anyString(), any(StoreCreateServiceRequest.class)))
            .willReturn(response);

        mockMvc.perform(
            multipart(BASE_URL)
                .file(image)
                .part(new MockPart("storeType", request.getStoreType().getBytes()))
                .part(new MockPart("storeName", request.getStoreName().getBytes()))
                .part(new MockPart("storeDescription", request.getStoreDescription().getBytes()))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
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
                requestParts(
                    partWithName("storeType")
                        .description("신규 등록할 매장 타입"),
                    partWithName("storeName")
                        .description("신규 등록할 매장명"),
                    partWithName("storeDescription")
                        .optional()
                        .description("신규 등록할 매장 설명"),
                    partWithName("storeImage")
                        .optional()
                        .description("신규 등록할 매장 이미지")
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
                        .description("신규 등록된 매장 식별키"),
                    fieldWithPath("data.storeType").type(JsonFieldType.STRING)
                        .description("신규 등록된 매장 타입"),
                    fieldWithPath("data.storeName").type(JsonFieldType.STRING)
                        .description("신규 등록된 매장명"),
                    fieldWithPath("data.storeDescription").type(JsonFieldType.STRING)
                        .optional()
                        .description("신규 등록된 매장 설명"),
                    fieldWithPath("data.storeImage").type(JsonFieldType.STRING)
                        .optional()
                        .description("신규 등록된 매장 이미지 URL"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("신규 매장 등록 일시")
                )
            ));
    }
}
