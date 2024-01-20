package com.foodwallet.server.domain.store.repository;

import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.dto.StoreSearchCond;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.foodwallet.server.domain.store.QStore.store;
import static com.foodwallet.server.domain.store.StoreStatus.OPEN;
import static org.springframework.util.StringUtils.*;

@Repository
public class StoreQueryRepository {

    private final JPAQueryFactory queryFactory;

    public StoreQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Slice<StoreResponse> findAllByCond(StoreSearchCond cond, Pageable pageable) {
        List<StoreResponse> contents = queryFactory
            .select(
                Projections.constructor(StoreResponse.class,
                    store.id,
                    store.type,
                    store.name,
                    store.operationalInfo.address,
                    store.operationalInfo.openTime,
                    store.image.storeFileName,
                    store.reviewInfo.avgRate
                )
            )
            .from(store)
            .where(
                store.isDeleted.isFalse(),
                store.status.eq(OPEN),
                eqType(cond.getType()),
                containsName(cond.getQuery())
            )
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

    private BooleanExpression eqType(StoreType type) {
        return type == null ? null : store.type.eq(type);
    }

    private BooleanExpression containsName(String name) {
        return hasText(name) ? store.name.contains(name) : null;
    }
}
