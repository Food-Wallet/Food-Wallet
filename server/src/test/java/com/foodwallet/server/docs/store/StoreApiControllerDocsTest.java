package com.foodwallet.server.docs.store;

import com.foodwallet.server.api.controller.store.StoreApiController;
import com.foodwallet.server.api.controller.store.request.*;
import com.foodwallet.server.api.service.store.StoreService;
import com.foodwallet.server.api.service.store.request.StoreCreateServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreModifyInfoServiceRequest;
import com.foodwallet.server.api.service.store.request.StoreOpenServiceRequest;
import com.foodwallet.server.api.service.store.response.*;
import com.foodwallet.server.docs.RestDocsSupport;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.security.SecurityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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

    @DisplayName("매장 정보 수정 API")
    @Test
    void modifyStoreInfo() throws Exception {
        StoreModifyInfoRequest request = StoreModifyInfoRequest.builder()
            .storeType("CHICKEN")
            .storeName("나리닭강정 본점")
            .storeDescription("국내 1위 닭강정 맛집 본점!")
            .build();

        StoreModifyInfoResponse response = StoreModifyInfoResponse.builder()
            .storeId(1L)
            .storeType("치킨")
            .storeName("나리닭강정 본점")
            .storeDescription("국내 1위 닭강정 맛집 본점!")
            .modifiedDateTime(LocalDateTime.of(2024, 1, 28, 4, 51))
            .build();

        given(storeService.modifyStoreInfo(anyString(), anyLong(), any(StoreModifyInfoServiceRequest.class)))
            .willReturn(response);

        mockMvc.perform(
                patch(BASE_URL + "/{storeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
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
                    fieldWithPath("storeType").type(JsonFieldType.STRING)
                        .description("수정할 매장 타입"),
                    fieldWithPath("storeName").type(JsonFieldType.STRING)
                        .description("수정할 매장명"),
                    fieldWithPath("storeDescription").type(JsonFieldType.STRING)
                        .optional()
                        .description("수정할 매장 설명")
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
                        .description("수정된 매장 식별키"),
                    fieldWithPath("data.storeType").type(JsonFieldType.STRING)
                        .description("수정된 매장 타입"),
                    fieldWithPath("data.storeName").type(JsonFieldType.STRING)
                        .description("수정된 매장명"),
                    fieldWithPath("data.storeDescription").type(JsonFieldType.STRING)
                        .optional()
                        .description("수정된 매장 설명"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("매장 정보 수정 일시")
                )
            ));
    }

    @DisplayName("매장 이미지 수정 API")
    @Test
    void modifyStoreImage() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile(
            "storeImage",
            "store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        StoreModifyImageRequest request = StoreModifyImageRequest.builder()
            .storeImage(image)
            .build();

        StoreModifyImageResponse response = StoreModifyImageResponse.builder()
            .storeId(1L)
            .storeName("나리닭강정")
            .storeImage("s3-store-image-url")
            .modifiedDateTime(LocalDateTime.of(2024, 1, 28, 6, 22))
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
                        .description("이미지 수정된 매장 식별키"),
                    fieldWithPath("data.storeName").type(JsonFieldType.STRING)
                        .description("이미지 수정된 매장명"),
                    fieldWithPath("data.storeImage").type(JsonFieldType.STRING)
                        .optional()
                        .description("이미지 수정된 매장 이미지 URL"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("매장 이미지 수정 일시")
                )
            ));
    }

    @DisplayName("매장 운영 시작 API")
    @Test
    void openStore() throws Exception {
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .startTime(LocalTime.of(11, 0))
            .finishTime(LocalTime.of(20, 0))
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();

        OperationOpenResponse operationInfo = OperationOpenResponse.builder()
            .operationId(1L)
            .address("경기도 성남시 분당구 판교역로 166")
            .time("오전 11:00 ~ 오후 8:00")
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();
        StoreOpenResponse response = StoreOpenResponse.builder()
            .storeId(1L)
            .status(StoreStatus.OPEN.getText())
            .storeName("나리닭강정")
            .operationInfo(operationInfo)
            .startedDateTime(LocalDateTime.of(2024, 1, 28, 10, 50))
            .build();

        given(storeService.openStore(anyString(), anyLong(), any(StoreOpenServiceRequest.class)))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/{storeId}/open", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
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
                        .description("매장 운영할 장소의 도로명 주소"),
                    fieldWithPath("startTime").type(JsonFieldType.ARRAY)
                        .description("매장 운영을 시작할 시간"),
                    fieldWithPath("finishTime").type(JsonFieldType.ARRAY)
                        .description("매장 운영을 종료할 시간"),
                    fieldWithPath("latitude").type(JsonFieldType.NUMBER)
                        .description("매장 운영할 장소의 위도"),
                    fieldWithPath("longitude").type(JsonFieldType.NUMBER)
                        .description("매장 운영할 장소의 경도")
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
                        .description("운영을 시작한 매장 식별키"),
                    fieldWithPath("data.status").type(JsonFieldType.STRING)
                        .description("매장 운영 상태"),
                    fieldWithPath("data.storeName").type(JsonFieldType.STRING)
                        .description("운영을 시작한 매장명"),
                    fieldWithPath("data.operationInfo").type(JsonFieldType.OBJECT)
                        .description("매장 운영 정보"),
                    fieldWithPath("data.operationInfo.operationId").type(JsonFieldType.NUMBER)
                        .description("매장 운영 정보 식별키"),
                    fieldWithPath("data.operationInfo.address").type(JsonFieldType.STRING)
                        .description("매장 운영할 장소의 도로명 주소"),
                    fieldWithPath("data.operationInfo.time").type(JsonFieldType.STRING)
                        .description("매장 운영 시간 정보"),
                    fieldWithPath("data.operationInfo.latitude").type(JsonFieldType.NUMBER)
                        .description("매장 운영할 장소의 위도"),
                    fieldWithPath("data.operationInfo.longitude").type(JsonFieldType.NUMBER)
                        .description("매장 운영할 장소의 경도"),
                    fieldWithPath("data.startedDateTime").type(JsonFieldType.ARRAY)
                        .description("운영 시작 일시")
                )
            ));
    }

    @DisplayName("매장 운영 종료 API")
    @Test
    void closeStore() throws Exception {
        OperationCloseResponse operationInfo = OperationCloseResponse.builder()
            .operationId(1L)
            .address("경기도 성남시 분당구 판교역로 166")
            .time("오전 11:00 ~ 오후 8:00")
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();
        StoreCloseResponse response = StoreCloseResponse.builder()
            .storeId(1L)
            .status(StoreStatus.CLOSE.getText())
            .storeName("나리닭강정")
            .operationInfo(operationInfo)
            .finishedDateTime(LocalDateTime.of(2024, 1, 28, 20, 10))
            .build();

        given(storeService.closeStore(anyString(), anyLong(), any(LocalDateTime.class)))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/{storeId}/close", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
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
                    fieldWithPath("data.storeId").type(JsonFieldType.NUMBER)
                        .description("운영을 종료한 매장 식별키"),
                    fieldWithPath("data.status").type(JsonFieldType.STRING)
                        .description("매장 운영 상태"),
                    fieldWithPath("data.storeName").type(JsonFieldType.STRING)
                        .description("운영을 종료한 매장명"),
                    fieldWithPath("data.operationInfo").type(JsonFieldType.OBJECT)
                        .description("매장 운영 정보"),
                    fieldWithPath("data.operationInfo.operationId").type(JsonFieldType.NUMBER)
                        .description("매장 운영 정보 식별키"),
                    fieldWithPath("data.operationInfo.address").type(JsonFieldType.STRING)
                        .description("매장 운영한 장소의 도로명 주소"),
                    fieldWithPath("data.operationInfo.time").type(JsonFieldType.STRING)
                        .description("매장 운영 시간 정보"),
                    fieldWithPath("data.operationInfo.latitude").type(JsonFieldType.NUMBER)
                        .description("매장 운영한 장소의 위도"),
                    fieldWithPath("data.operationInfo.longitude").type(JsonFieldType.NUMBER)
                        .description("매장 운영한 장소의 경도"),
                    fieldWithPath("data.finishedDateTime").type(JsonFieldType.ARRAY)
                        .description("운영 종료 일시")
                )
            ));
    }

    @DisplayName("매장 영구 삭제 API")
    @Test
    void removeStore() throws Exception {
        StoreRemoveRequest request = StoreRemoveRequest.builder()
            .currentPwd("dong1234!")
            .build();

        StoreRemoveResponse response = StoreRemoveResponse.builder()
            .storeId(1L)
            .storeType("치킨")
            .storeName("나리닭강정")
            .removedDateTime(LocalDateTime.of(2024, 1, 28, 17, 20))
            .build();

        given(storeService.removeStore(anyString(), anyLong(), anyString(), any(LocalDateTime.class)))
            .willReturn(response);

        mockMvc.perform(
                post(BASE_URL + "/{storeId}/remove", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
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
                requestFields(
                    fieldWithPath("currentPwd").type(JsonFieldType.STRING)
                        .description("현재 비밀번호")
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
                        .description("영구 삭제한 매장 식별키"),
                    fieldWithPath("data.storeType").type(JsonFieldType.STRING)
                        .description("영구 삭제된 매장 타입"),
                    fieldWithPath("data.storeName").type(JsonFieldType.STRING)
                        .description("영구 삭제된 매장명"),
                    fieldWithPath("data.removedDateTime").type(JsonFieldType.ARRAY)
                        .description("매장 영구 삭제 일시")
                )
            ));
    }
}
