package com.foodwallet.server.docs.review;

import com.foodwallet.server.api.controller.review.ReviewApiController;
import com.foodwallet.server.api.controller.review.request.ReviewReplyRequest;
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

    @DisplayName("리뷰 등록 API")
    @Test
    void createReview() throws Exception {
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

        mockMvc.perform(
                multipart(BASE_URL)
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

    @DisplayName("리뷰 답글 등록 API")
    @Test
    void replyReview() throws Exception {
        ReviewReplyRequest request = ReviewReplyRequest.builder()
            .replyContent("맛있게 드셔주셔서 감사합니다!")
            .build();
        mockMvc.perform(
                post(BASE_URL + "/{reviewId}/reply", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("reply-review",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                pathParameters(
                    parameterWithName("reviewId")
                        .description("리뷰 식별키")
                ),
                requestFields(
                    fieldWithPath("replyContent").type(JsonFieldType.STRING)
                        .description("답글 내용")
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
                    fieldWithPath("data.replyContent").type(JsonFieldType.STRING)
                        .description("답글 내용"),
                    fieldWithPath("data.replyCreatedDateTime").type(JsonFieldType.ARRAY)
                        .description("답글 등록 일시")
                )
            ));
    }

    @DisplayName("리뷰 조회 API")
    @Test
    void searchReviews() throws Exception {
        mockMvc.perform(
                get(BASE_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParam("page", "1")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-reviews",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                queryParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호")
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
                    fieldWithPath("data.content").type(JsonFieldType.ARRAY)
                        .description("리뷰 데이터"),
                    fieldWithPath("data.content[].reviewId").type(JsonFieldType.NUMBER)
                        .description("리뷰 식별키"),
                    fieldWithPath("data.content[].storeName").type(JsonFieldType.STRING)
                        .description("리뷰 작성한 매장명"),
                    fieldWithPath("data.content[].rate").type(JsonFieldType.NUMBER)
                        .description("리뷰 평점"),
                    fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                        .description("리뷰 내용"),
                    fieldWithPath("data.content[].reviewImages").type(JsonFieldType.ARRAY)
                        .description("리뷰 이미지 URL"),
                    fieldWithPath("data.content[].createdDateTime").type(JsonFieldType.ARRAY)
                        .description("리뷰 작성일"),
                    fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER)
                        .description("현재 페이지 번호"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("데이터 요청 갯수"),
                    fieldWithPath("data.isFirst").type(JsonFieldType.BOOLEAN)
                        .description("첫 페이지 여부"),
                    fieldWithPath("data.isLast").type(JsonFieldType.BOOLEAN)
                        .description("마지막 페이지 여부")
                )
            ));
    }

    @DisplayName("리뷰 삭제 API")
    @Test
    void removeReview() throws Exception {
        mockMvc.perform(
                delete(BASE_URL + "/{reviewId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer jwt.access.token")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-review",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("JWT 접근 토큰")
                ),
                pathParameters(
                    parameterWithName("reviewId")
                        .description("리뷰 식별키")
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
                    fieldWithPath("data.removedDateTime").type(JsonFieldType.ARRAY)
                        .description("리뷰 삭제 일시")
                )
            ));
    }
}
