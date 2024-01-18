package com.foodwallet.server.api.service.review.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewRemoveResponse {

    private final Long reviewId;
    private final LocalDateTime removedDateTime;

    @Builder
    private ReviewRemoveResponse(Long reviewId, LocalDateTime removedDateTime) {
        this.reviewId = reviewId;
        this.removedDateTime = removedDateTime;
    }
}
