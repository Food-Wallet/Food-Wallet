package com.foodwallet.server.api.controller.review;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.controller.review.request.ReviewCreateRequest;
import com.foodwallet.server.api.service.review.response.ReviewCreateResponse;
import com.foodwallet.server.api.service.review.response.ReviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewApiController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReviewCreateResponse> createReview(@Valid @ModelAttribute ReviewCreateRequest request) {
        ReviewCreateResponse response = ReviewCreateResponse.builder()
            .reviewId(1L)
            .createdDateTime(LocalDateTime.of(2024, 1, 17, 19, 43))
            .build();
        return ApiResponse.created(response);
    }

    @GetMapping
    public ApiResponse<SliceResponse<ReviewResponse>> searchReviews(
        @RequestParam Integer page
    ) {
        ReviewResponse response = ReviewResponse.builder()
            .reviewId(1L)
            .storeName("나리닭강정")
            .rate(5)
            .content("소문대로 정말 맛있어요!")
            .reviewImages(List.of("s3-store-review-image1-url.jpg", "s3-store-review-image1-url.jpg"))
            .createdDateTime(LocalDateTime.of(2024, 1, 18, 15, 0))
            .build();
        PageRequest pageable = PageRequest.of(0, 10);
        SliceImpl<ReviewResponse> slice = new SliceImpl<>(List.of(response), pageable, false);

        SliceResponse<ReviewResponse> data = SliceResponse.of(slice);
        return ApiResponse.ok(data);
    }
}
