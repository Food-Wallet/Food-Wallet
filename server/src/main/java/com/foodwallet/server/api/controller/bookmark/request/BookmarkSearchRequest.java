package com.foodwallet.server.api.controller.bookmark.request;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BookmarkSearchRequest {

    @Positive(message = "페이지 번호는 양수여야 합니다.")
    private Integer page;

    @Builder
    private BookmarkSearchRequest(Integer page) {
        this.page = page;
    }
}
