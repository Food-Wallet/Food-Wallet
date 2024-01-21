package com.foodwallet.server.api.controller.bookmark;

import com.foodwallet.server.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookmarkQueryApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/bookmarks";

    @DisplayName("회원이 즐겨찾기한 매장의 정보를 조회한다.")
    @Test
    void searchStores() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("page", "1")
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("회원이 즐겨찾기한 매장의 정보를 조회한다.")
    @Test
    void searchStoresWithZeroPage() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                get(BASE_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("page", "0")
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("페이지 번호는 양수여야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }
}