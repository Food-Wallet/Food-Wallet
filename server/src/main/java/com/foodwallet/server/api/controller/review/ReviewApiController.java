package com.foodwallet.server.api.controller.review;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.review.request.ReviewCreateRequest;
import com.foodwallet.server.api.service.review.response.ReviewCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
}
