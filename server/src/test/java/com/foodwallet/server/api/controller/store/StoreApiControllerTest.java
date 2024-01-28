package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.ControllerTestSupport;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyInfoRequest;
import com.foodwallet.server.api.controller.store.request.StoreOpenRequest;
import com.foodwallet.server.api.controller.store.request.StoreRemoveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import java.time.LocalTime;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StoreApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/stores";

    @DisplayName("신규 매장을 등록한다.")
    @Test
    void createStore() throws Exception {
        //given
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

        //when //then
        mockMvc.perform(
                multipart(BASE_URL)
                    .file(image)
                    .part(new MockPart("storeType", request.getStoreType().getBytes()))
                    .part(new MockPart("storeName", request.getStoreName().getBytes()))
                    .part(new MockPart("storeDescription", request.getStoreDescription().getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @DisplayName("신규 매장 등록할 때 매장 타입은 필수값이다.")
    @Test
    void createStoreWithoutStoreType() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile(
            "storeImage",
            "store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        StoreCreateRequest request = StoreCreateRequest.builder()
            .storeName("나리닭강정")
            .storeDescription("국내 1등 닭강정 맛집!")
            .storeImage(image)
            .build();

        //when //then
        mockMvc.perform(
                multipart(BASE_URL)
                    .file(image)
                    .part(new MockPart("storeName", request.getStoreName().getBytes()))
                    .part(new MockPart("storeDescription", request.getStoreDescription().getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("매장 타입을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 매장 등록할 때 매장명은 필수값이다.")
    @Test
    void createStoreWithoutStoreName() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile(
            "storeImage",
            "store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        StoreCreateRequest request = StoreCreateRequest.builder()
            .storeType("CHICKEN")
            .storeDescription("국내 1등 닭강정 맛집!")
            .storeImage(image)
            .build();

        //when //then
        mockMvc.perform(
                multipart(BASE_URL)
                    .file(image)
                    .part(new MockPart("storeType", request.getStoreType().getBytes()))
                    .part(new MockPart("storeDescription", request.getStoreDescription().getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("매장명을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 정보를 수정한다.")
    @Test
    void modifyStoreInfo() throws Exception {
        //given
        StoreModifyInfoRequest request = StoreModifyInfoRequest.builder()
            .storeType("CHICKEN")
            .storeName("나리닭강정 본점")
            .storeDescription("국내 1위 닭강정 맛집 본점!")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("매장 정보를 수정할 때 매장 타입은 필수값이다.")
    @Test
    void modifyStoreInfoWithoutStoreType() throws Exception {
        //given
        StoreModifyInfoRequest request = StoreModifyInfoRequest.builder()
            .storeName("나리닭강정 본점")
            .storeDescription("국내 1위 닭강정 맛집 본점!")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("매장 타입을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 정보를 수정할 때 매장명은 필수값이다.")
    @Test
    void modifyStoreInfoWithoutStoreName() throws Exception {
        //given
        StoreModifyInfoRequest request = StoreModifyInfoRequest.builder()
            .storeType("CHICKEN")
            .storeDescription("국내 1위 닭강정 맛집 본점!")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("매장명을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 이미지를 수정한다.")
    @Test
    void modifyStoreImage() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile(
            "storeImage",
            "store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        //when //then
        mockMvc.perform(
                multipart(BASE_URL + "/{storeId}/image", 1)
                    .file(image)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("매장 운영을 시작한다.")
    @Test
    void openStore() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .startTime(LocalTime.of(11, 0))
            .finishTime(LocalTime.of(20, 0))
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}/open", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("매장 운영을 시작할 때 주소는 필수값이다.")
    @Test
    void openStoreWithoutAddress() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .startTime(LocalTime.of(11, 0))
            .finishTime(LocalTime.of(20, 0))
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}/open", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("주소를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 운영을 시작할 때 운영 시작 시간은 필수값이다.")
    @Test
    void openStoreWithStartTime() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .finishTime(LocalTime.of(20, 0))
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}/open", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("시작 시간을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 운영을 시작할 때 운영 종료 시간은 필수값이다.")
    @Test
    void openStoreWithoutFinishTime() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .startTime(LocalTime.of(11, 0))
            .latitude(37.3954951)
            .longitude(127.1103645)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}/open", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("종료 시간을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 운영을 시작할 때 위도는 필수값이다.")
    @Test
    void openStoreWithoutLatitude() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .startTime(LocalTime.of(11, 0))
            .finishTime(LocalTime.of(20, 0))
            .longitude(127.1103645)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}/open", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("위도를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 운영을 시작할 때 경도는 필수값이다.")
    @Test
    void openStoreWithoutLongitude() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("경기도 성남시 분당구 판교역로 166")
            .startTime(LocalTime.of(11, 0))
            .finishTime(LocalTime.of(20, 0))
            .latitude(37.3954951)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}/open", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("경도를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 운영을 종료한다.")
    @Test
    void closeStore() throws Exception {
        //given //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}/close", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("매장을 영구 삭제한다.")
    @Test
    void removeStore() throws Exception {
        //given
        StoreRemoveRequest request = StoreRemoveRequest.builder()
            .currentPwd("dong1234!")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}/remove", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("매장을 영구 삭제할 때 현재 비밀번호는 필수값이다.")
    @Test
    void removeStoreWithoutCurrentPwd() throws Exception {
        //given
        StoreRemoveRequest request = StoreRemoveRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}/remove", 1)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("비밀번호를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }
}