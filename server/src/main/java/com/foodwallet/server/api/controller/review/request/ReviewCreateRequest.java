package com.foodwallet.server.api.controller.review.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {

    private Long orderId;
    private Integer rate;
    private String content;
    private List<MultipartFile> images;

    @Builder
    private ReviewCreateRequest(Long orderId, Integer rate, String content, List<MultipartFile> images) {
        this.orderId = orderId;
        this.rate = rate;
        this.content = content;
        this.images = images;
    }
}
