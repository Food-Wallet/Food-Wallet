package com.foodwallet.server.api.controller.store;

import com.foodwallet.server.ControllerTestSupport;
import com.foodwallet.server.api.controller.store.request.StoreCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}