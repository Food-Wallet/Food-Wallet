package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.ControllerTestSupport;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
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
}