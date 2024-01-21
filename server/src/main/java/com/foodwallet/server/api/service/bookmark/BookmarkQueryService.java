package com.foodwallet.server.api.service.bookmark;

import com.foodwallet.server.api.SliceResponse;
import com.foodwallet.server.domain.bookmark.repository.BookmarkQueryRepository;
import com.foodwallet.server.domain.bookmark.repository.response.BookmarkResponse;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookmarkQueryService {

    private final BookmarkQueryRepository bookmarkQueryRepository;
    private final MemberRepository memberRepository;

    public SliceResponse<BookmarkResponse> searchBookmarks(String email, Pageable pageable) {
        Member member = memberRepository.findByEmail(email);

        Slice<BookmarkResponse> content = bookmarkQueryRepository.findAllByMemberId(member.getId(), pageable);

        return SliceResponse.of(content);
    }
}
