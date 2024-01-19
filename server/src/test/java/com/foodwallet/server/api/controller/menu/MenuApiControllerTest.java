package com.foodwallet.server.api.controller.menu;

import com.foodwallet.server.ControllerTestSupport;
import com.foodwallet.server.api.controller.menu.request.MenuCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/stores/{storeId}/menus";

    @DisplayName("신규 메뉴를 등록한다.")
    @Test
    void createMenu() throws Exception {
        //given
        MenuCreateRequest request = MenuCreateRequest.builder()
            .name("간장닭강정")
            .price(8000)
            .build();

        //when //then
        mockMvc.perform(
                multipart(BASE_URL, 1)
                    .part(new MockPart("name", request.getName().getBytes()))
                    .part(new MockPart("price", request.getPrice().toString().getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @DisplayName("신규 메뉴를 등록할 때 메뉴명은 필수값이다.")
    @Test
    void createMenuWithoutName() throws Exception {
        //given
        MenuCreateRequest request = MenuCreateRequest.builder()
            .price(8000)
            .build();

        //when //then
        mockMvc.perform(
                multipart(BASE_URL, 1)
                    .part(new MockPart("name", request.getName().getBytes()))
                    .part(new MockPart("price", request.getPrice().toString().getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("메뉴명은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 메뉴를 등록할 때 가격은 양수이다.")
    @Test
    void createMenuWithZeroPrice() throws Exception {
        //given
        MenuCreateRequest request = MenuCreateRequest.builder()
            .name("간장닭강정")
            .price(0)
            .build();

        //when //then
        mockMvc.perform(
                multipart(BASE_URL, 1)
                    .part(new MockPart("name", request.getName().getBytes()))
                    .part(new MockPart("price", request.getPrice().toString().getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("메뉴 가격은 양수여야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }
}