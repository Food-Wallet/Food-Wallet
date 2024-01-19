package com.foodwallet.server.api.controller.menu;

import com.foodwallet.server.ControllerTestSupport;
import com.foodwallet.server.api.controller.menu.request.MenuCreateRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyRequest;
import com.foodwallet.server.api.controller.menu.request.MenuModifyStatusRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
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

    @DisplayName("메뉴 정보를 수정한다.")
    @Test
    void modifyMenuInfo() throws Exception {
        //given
        MenuModifyRequest request = MenuModifyRequest.builder()
            .name("간장닭강정")
            .price(9000)
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{menuId}", 1, 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("메뉴 정보를 수정할 때 메뉴명은 필수값이다.")
    @Test
    void modifyMenuInfoWithoutName() throws Exception {
        //given
        MenuModifyRequest request = MenuModifyRequest.builder()
            .price(9000)
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{menuId}", 1, 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("메뉴명은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("메뉴 정보를 수정할 때 가격은 양수이다.")
    @Test
    void modifyMenuInfoWithZeroPrice() throws Exception {
        //given
        MenuModifyRequest request = MenuModifyRequest.builder()
            .name("간장닭강정")
            .price(0)
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{menuId}", 1, 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("메뉴 가격은 양수여야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("메뉴 이미지를 수정한다.")
    @Test
    void modifyMenuImage() throws Exception {
        //given
        MockMultipartFile image = new MockMultipartFile(
            "image",
            "my-store-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        //when //then
        mockMvc.perform(
                multipart(BASE_URL + "/{menuId}/image", 1, 1)
                    .file(image)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .with(csrf())
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("메뉴 판매 상태를 수정한다.")
    @Test
    void modifyMenuStatus() throws Exception {
        //given
        MenuModifyStatusRequest request = MenuModifyStatusRequest.builder()
            .status("STOP_SELLING")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{menuId}/status", 1, 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("메뉴 판매 상태를 수정할 때 판매 상태는 필수값이다.")
    @Test
    void modifyMenuStatusWithoutStatus() throws Exception {
        //given
        MenuModifyStatusRequest request = MenuModifyStatusRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/{menuId}/status", 1, 1)
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("판매 상태는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("메뉴를 삭제한다.")
    @Test
    void removeMenu() throws Exception {
        //given //when //then
        mockMvc.perform(
                delete(BASE_URL + "/{menuId}", 1, 1)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}