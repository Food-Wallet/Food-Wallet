package com.foodwallet.server.api.controller.review.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewReplyRequest {

    private String replyContent;

    @Builder
    private ReviewReplyRequest(String replyContent) {
        this.replyContent = replyContent;
    }
}
