package com.foodwallet.server.api.controller.bookmark;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.api.service.bookmark.BookmarkQueryService;
import com.foodwallet.server.domain.bookmark.repository.response.BookmarkResponse;
import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.foodwallet.server.domain.store.StoreStatus.OPEN;
import static com.foodwallet.server.domain.store.StoreType.CHICKEN;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmarks")
public class BookmarkQueryApiController {

    private final BookmarkQueryService bookmarkQueryService;

    @GetMapping
    public ApiResponse<SliceResponse<BookmarkResponse>> searchBookmarks(
        @RequestParam Integer page
    ) {
        BookmarkResponse response = BookmarkResponse.builder()
            .storeId(1L)
            .status(OPEN)
            .type(CHICKEN)
            .storeName("나리닭강정")
            .avgRate(5.0)
            .reviewCount(100)
            .storeImageUrl("s3-store-image-url")
            .build();
        PageRequest pageable = PageRequest.of(0, 10);
        SliceImpl<BookmarkResponse> slice = new SliceImpl<>(List.of(response), pageable, false);

        SliceResponse<BookmarkResponse> data = SliceResponse.of(slice);

        return ApiResponse.ok(data);
    }
}
