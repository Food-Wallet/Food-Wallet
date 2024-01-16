package com.foodwallet.server.docs.menu;

import com.foodwallet.server.api.controller.menu.MenuApiController;
import com.foodwallet.server.api.controller.menu.request.MenuModifyRequest;
import com.foodwallet.server.api.controller.store.request.StoreModifyRequest;
import com.foodwallet.server.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/stores/{storeId}/menus";

    @Override
    protected Object initController() {
        return new MenuApiController();
    }

    @DisplayName("메뉴 등록 API")
    @Test
    void createMenu() throws Exception {
        MockMultipartFile image = new MockMultipartFile(
            "image",
            "store-menu-image.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        mockMvc.perform(
                multipart(BASE_URL, 1)
                    .file(image)
                    .part(new MockPart("name", "간장닭강정".getBytes()))
                    .part(new MockPart("price", "8000".getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-menu",
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
                    partWithName("name")
                        .description("메뉴 이름"),
                    partWithName("description")
                        .optional()
                        .description("메뉴 설명"),
                    partWithName("price")
                        .description("메뉴 가격"),
                    partWithName("image")
                        .optional()
                        .description("메뉴 이미지")
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
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("등록된 메뉴 이름"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .optional()
                        .description("등록된 메뉴 설명"),
                    fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                        .description("등록된 메뉴 가격"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("메뉴 등록 일시")
                )
            ));
    }

    @DisplayName("메뉴 정보 수정 API")
    @Test
    void modifyMenuInfo() throws Exception {
        MenuModifyRequest request = MenuModifyRequest.builder()
            .name("간장닭강정")
            .description("우리 매장 시그니처 메뉴입니다!")
            .price(8000)
            .build();

        mockMvc.perform(
                patch(BASE_URL + "/{menuId}", 1, 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-menu-info",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                pathParameters(
                    parameterWithName("storeId")
                        .description("매장 식별키"),
                    parameterWithName("menuId")
                        .description("메뉴 식별키")
                ),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("메뉴 이름"),
                    fieldWithPath("description").type(JsonFieldType.STRING)
                        .optional()
                        .description("메뉴 설명"),
                    fieldWithPath("price").type(JsonFieldType.NUMBER)
                        .description("메뉴 가격")
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
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("수정된 메뉴 이름"),
                    fieldWithPath("data.description").type(JsonFieldType.STRING)
                        .optional()
                        .description("수정된 메뉴 설명"),
                    fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                        .description("수정된 메뉴 가격"),
                    fieldWithPath("data.modifiedDateTime").type(JsonFieldType.ARRAY)
                        .description("메뉴 정보 수정 일시")
                )
            ));
    }
}
