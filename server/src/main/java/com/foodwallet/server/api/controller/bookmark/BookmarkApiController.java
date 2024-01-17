package com.foodwallet.server.api.controller.bookmark;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCancelResponse;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCreateResponse;
import com.foodwallet.server.api.service.bookmark.response.BookmarkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/bookmarks")
    public ApiResponse<SliceResponse<BookmarkResponse>> searchBookmarks(
        @RequestParam Integer page
    ) {
        BookmarkResponse response = BookmarkResponse.builder()
            .storeId(1L)
            .type("치킨")
            .name("나리닭강정")
            .address("서울 중구 세종대로 110")
            .openTime("오전 11:00 ~ 오후 8:00")
            .storeImage("s3-store-img-url")
            .avgRate(5.0)
            .build();
        PageRequest pageable = PageRequest.of(0, 10);
        SliceImpl<BookmarkResponse> slice = new SliceImpl<>(List.of(response), pageable, false);

        SliceResponse<BookmarkResponse> data = SliceResponse.of(slice);

        return ApiResponse.ok(data);
    }
}
