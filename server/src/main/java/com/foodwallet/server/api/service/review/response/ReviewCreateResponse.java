package com.foodwallet.server.api.service.review.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewCreateResponse {

    private final Long reviewId;
    private final LocalDateTime createdDateTime;

    @Builder
    private ReviewCreateResponse(Long reviewId, LocalDateTime createdDateTime) {
        this.reviewId = reviewId;
        this.createdDateTime = createdDateTime;
    }
}
