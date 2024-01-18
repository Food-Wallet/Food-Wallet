package com.foodwallet.server.api.service.review.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewReplyResponse {

    private final Long reviewId;
    private final String replyContent;
    private final LocalDateTime replyCreatedDateTime;

    @Builder
    private ReviewReplyResponse(Long reviewId, String replyContent, LocalDateTime replyCreatedDateTime) {
        this.reviewId = reviewId;
        this.replyContent = replyContent;
        this.replyCreatedDateTime = replyCreatedDateTime;
    }
}
