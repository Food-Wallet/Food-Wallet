package com.foodwallet.server.api.controller.bookmark;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCancelResponse;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookmarkApiController {

    @PostMapping("/stores/{storeId}/bookmark")
    public ApiResponse<BookmarkCreateResponse> createBookmark(@PathVariable Long storeId) {
        BookmarkCreateResponse response = BookmarkCreateResponse.builder()
            .storeName("나리닭강정")
            .build();
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/stores/{storeId}/bookmark")
    public ApiResponse<BookmarkCancelResponse> cancelBookmark(@PathVariable Long storeId) {
        BookmarkCancelResponse response = BookmarkCancelResponse.builder()
            .storeName("나리닭강정")
            .build();
        return ApiResponse.ok(response);
    }
}
