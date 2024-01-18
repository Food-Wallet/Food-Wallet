package com.foodwallet.server.api.service.review.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponse {

    private final Long reviewId;
    private final String storeName;
    private final int rate;
    private final String content;
    private final List<String> reviewImages;
    private final LocalDateTime createdDateTime;

    @Builder
    private ReviewResponse(Long reviewId, String storeName, int rate, String content, List<String> reviewImages, LocalDateTime createdDateTime) {
        this.reviewId = reviewId;
        this.storeName = storeName;
        this.rate = rate;
        this.content = content;
        this.reviewImages = reviewImages;
        this.createdDateTime = createdDateTime;
    }
}
