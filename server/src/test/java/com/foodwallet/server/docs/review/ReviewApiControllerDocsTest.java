package com.foodwallet.server.docs.review;

import com.foodwallet.server.api.controller.review.ReviewApiController;
import com.foodwallet.server.api.controller.review.request.ReviewCreateRequest;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewApiControllerDocsTest extends RestDocsSupport {

    private static final String BASE_URL = "/api/v1/reviews";

    @Override
    protected Object initController() {
        return new ReviewApiController();
    }

    @DisplayName("주문 내역 삭제 API")
    @Test
    void removeOrder() throws Exception {
        MockMultipartFile image1 = new MockMultipartFile(
            "images",
            "store-review-image1.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        MockMultipartFile image2 = new MockMultipartFile(
            "images",
            "store-review-image2.jpg",
            "image/jpg",
            "<<image data>>".getBytes()
        );

        ReviewCreateRequest request = ReviewCreateRequest.builder()
            .orderId(1L)
            .rate(5)
            .content("소문대로 정말 맛있어요!")
            .build();

        mockMvc.perform(
                multipart(BASE_URL, 1)
                    .file(image1)
                    .file(image2)
                    .part(new MockPart("orderId", "1".getBytes()))
                    .part(new MockPart("rate", "5".getBytes()))
                    .part(new MockPart("content", "소문대로 정말 맛있어요!".getBytes()))
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-review",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                requestParts(
                    partWithName("orderId")
                        .description("리뷰 작성할 주문 식별키"),
                    partWithName("rate")
                        .description("리뷰 평점"),
                    partWithName("content")
                        .description("리뷰 내용"),
                    partWithName("images")
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
                    fieldWithPath("data.reviewId").type(JsonFieldType.NUMBER)
                        .description("리뷰 식별키"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("리뷰 등록 일시")
                )
            ));
    }
}
