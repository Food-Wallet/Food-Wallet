package com.foodwallet.server.api.controller.bookmark;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.controller.bookmark.request.BookmarkSearchRequest;
import com.foodwallet.server.api.service.bookmark.BookmarkQueryService;
import com.foodwallet.server.domain.bookmark.repository.response.BookmarkResponse;
import com.foodwallet.server.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.foodwallet.server.common.constant.CommonConst.PAGE_SIZE;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmarks")
public class BookmarkQueryApiController {

    private final BookmarkQueryService bookmarkQueryService;

    /**
     * 즐겨찾기 등록한 매장 정보 조회 API
     *
     * @param request 검색 조건
     * @return 검색 조건과 일치하는 매장 목록
     */
    @GetMapping
    public ApiResponse<SliceResponse<BookmarkResponse>> searchBookmarks(@Valid BookmarkSearchRequest request) {
        String email = SecurityUtils.getCurrentEmail();

        PageRequest pageRequest = PageRequest.of(request.getPage(), PAGE_SIZE);

        SliceResponse<BookmarkResponse> response = bookmarkQueryService.searchBookmarks(email, pageRequest);

        return ApiResponse.ok(response);
    }
}
