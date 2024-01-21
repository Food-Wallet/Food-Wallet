package com.foodwallet.server.api.controller.bookmark;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.service.bookmark.BookmarkService;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCancelResponse;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCreateResponse;
import com.foodwallet.server.api.service.bookmark.response.BookmarkResponse;
import com.foodwallet.server.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmarks")
public class BookmarkApiController {

    private final BookmarkService bookmarkService;

    /**
     * 매장 즐겨찾기 등록 API
     *
     * @param storeId 즐겨찾기 등록할 매장의 식별키
     * @return 즐겨찾기 등록된 매장의 정보
     */
    @PostMapping("/{storeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<BookmarkCreateResponse> createBookmark(@PathVariable Long storeId) {
        String email = SecurityUtils.getCurrentEmail();

        BookmarkCreateResponse response = bookmarkService.createBookmark(email, storeId);

        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{storeId}")
    public ApiResponse<BookmarkCancelResponse> cancelBookmark(@PathVariable Long storeId) {
        BookmarkCancelResponse response = BookmarkCancelResponse.builder()
            .storeName("나리닭강정")
            .build();
        return ApiResponse.ok(response);
    }

    @GetMapping
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
