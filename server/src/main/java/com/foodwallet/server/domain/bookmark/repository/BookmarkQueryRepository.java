package com.foodwallet.server.domain.bookmark.repository;

import com.foodwallet.server.api.service.bookmark.response.BookmarkResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public class BookmarkQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BookmarkQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Slice<BookmarkResponse> findByMemberId(Long memberId) {
        return null;
    }
}
