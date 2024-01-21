package com.foodwallet.server.api.controller.bookmark;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.service.bookmark.BookmarkService;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCancelResponse;
import com.foodwallet.server.api.service.bookmark.response.BookmarkCreateResponse;
import com.foodwallet.server.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 매장 즐겨찾기 취소 API
     *
     * @param storeId 즐겨찾기 취소할 매장의 식별키
     * @return 즐겨찾기 취소된 매장의 정보
     */
    @DeleteMapping("/{storeId}")
    public ApiResponse<BookmarkCancelResponse> cancelBookmark(@PathVariable Long storeId) {
        String email = SecurityUtils.getCurrentEmail();

        BookmarkCancelResponse response = bookmarkService.cancelBookmark(email, storeId);

        return ApiResponse.ok(response);
    }
}
