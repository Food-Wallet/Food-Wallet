package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.ControllerTestSupport;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyRequest;
import com.foodwallet.server.api.controller.store.request.StoreOpenRequest;
import com.foodwallet.server.api.controller.store.request.StoreRemoveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StoreApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/stores";

    @DisplayName("신규 매장을 등록한다.")
    @Test
    void createStore() throws Exception {
        //given
        StoreCreateRequest request = StoreCreateRequest.builder()
            .type("CHICKEN")
            .name("나리닭강정")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @DisplayName("신규 매장을 등록할 때 매장 타입은 필수값이다.")
    @Test
    void createStoreWithoutType() throws Exception {
        //given
        StoreCreateRequest request = StoreCreateRequest.builder()
            .name("나리닭강정")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("매장 타입은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 매장을 등록할 때 매장명은 필수값이다.")
    @Test
    void createProductWithoutName() throws Exception {
        //given
        StoreCreateRequest request = StoreCreateRequest.builder()
            .type("CHICKEN")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("매장명은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 정보를 수정한다.")
    @Test
    void modifyStoreInfo() throws Exception {
        //given
        StoreModifyRequest request = StoreModifyRequest.builder()
            .type("치킨")
            .name("나리닭강정")
            .description("대한민국에서 1등 닭강정!")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}", 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("매장 정보를 수정할 때 매장 타입은 필수값이다.")
    @Test
    void modifyStoreInfoWithoutType() throws Exception {
        //given
        StoreModifyRequest request = StoreModifyRequest.builder()
            .name("나리닭강정")
            .description("대한민국에서 1등 닭강정!")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}", 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("매장 타입은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 정보를 수정할 때 매장명은 필수값이다.")
    @Test
    void modifyStoreInfoWithoutName() throws Exception {
        //given
        StoreModifyRequest request = StoreModifyRequest.builder()
            .type("치킨")
            .description("대한민국에서 1등 닭강정!")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}", 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("매장명은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장을 오픈한다.")
    @Test
    void openStore() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("서울 중구 세종대로 110")
            .openTime("오전 10:00 ~ 오후 8:00")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}/open", 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("매장을 오픈할 때 현위치 도로명 주소는 필수값이다.")
    @Test
    void openStoreWithoutAddress() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .openTime("오전 10:00 ~ 오후 8:00")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}/open", 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("주소는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장을 오픈할 때 운영 시간은 필수값이다.")
    @Test
    void openStoreWithoutOpenTime() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("서울 중구 세종대로 110")
            .latitude(37.566352778)
            .longitude(126.977952778)
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}/open", 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("운영 시간은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장을 오픈할 때 위도는 필수값이다.")
    @Test
    void openStoreWithoutLatitude() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("서울 중구 세종대로 110")
            .openTime("오전 10:00 ~ 오후 8:00")
            .longitude(126.977952778)
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}/open", 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("위도는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장을 오픈할 때 경도는 필수값이다.")
    @Test
    void openStoreWithoutLongitude() throws Exception {
        //given
        StoreOpenRequest request = StoreOpenRequest.builder()
            .address("서울 중구 세종대로 110")
            .openTime("오전 10:00 ~ 오후 8:00")
            .latitude(37.566352778)
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}/open", 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("경도는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("매장 운영을 종료한다.")
    @Test
    void closeStore() throws Exception {
        //given //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{storeId}/close", 1)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("매장 이미지를 수정한다.")
    @Test
    void modifyStoreImage() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile(
            "image",
            "my-store-image.jpg",
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

    @DisplayName("매장을 삭제한다.")
    @Test
    void removeStore() throws Exception {
        //given
        StoreRemoveRequest request = StoreRemoveRequest.builder()
            .pwd("dong1234!")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}", 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("매장을 삭제할 때 현재 비밀번호는 필수값이다.")
    @Test
    void removeStoreWithoutPwd() throws Exception {
        //given
        StoreRemoveRequest request = StoreRemoveRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}", 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("비밀번호를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }
}