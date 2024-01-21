package com.foodwallet.server.api.controller.bookmark;

import com.foodwallet.server.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookmarkApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/bookmarks";

    @DisplayName("매장을 즐겨찾기에 등록한다.")
    @Test
    void createBookmark() throws Exception {
        //given //when //then
        mockMvc.perform(
                post(BASE_URL + "/{storeId}", 1)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated());
    }

}