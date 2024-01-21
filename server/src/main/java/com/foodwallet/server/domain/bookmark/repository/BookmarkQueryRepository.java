package com.foodwallet.server.domain.bookmark.repository;

import com.foodwallet.server.domain.bookmark.repository.response.BookmarkResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.foodwallet.server.domain.bookmark.QBookmark.*;

@Repository
public class BookmarkQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BookmarkQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Slice<BookmarkResponse> findByMemberId(Long memberId, Pageable pageable) {
        List<BookmarkResponse> contents = queryFactory
            .select(
                Projections.constructor(BookmarkResponse.class,
                    bookmark.store.id,
                    bookmark.store.status,
                    bookmark.store.type,
                    bookmark.store.name,
                    bookmark.store.reviewInfo.avgRate,
                    bookmark.store.reviewInfo.reviewCount,
                    bookmark.store.image.storeFileName
                )
            )
            .from(bookmark)
            .join(bookmark.store)
            .where(
                bookmark.isDeleted.isFalse(),
                bookmark.store.isDeleted.isFalse(),
                bookmark.member.id.eq(memberId)
            )
            .orderBy(bookmark.lastModifiedDateTime.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize() + 1)
            .fetch();

        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);
    }
}
