package com.foodwallet.server.domain.store.repository;

import com.foodwallet.server.domain.store.StoreType;
import com.foodwallet.server.domain.store.repository.dto.StoreSearchCond;
import com.foodwallet.server.domain.store.repository.dto.StoreDetailDto;
import com.foodwallet.server.domain.store.repository.response.StoreResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
        return null;
    }

    public StoreDetailDto findStoreDetailById(Long storeId) {
        return null;
    }

    private BooleanExpression eqType(StoreType type) {
        return type == null ? null : store.type.eq(type);
    }

    private BooleanExpression containsName(String name) {
        return hasText(name) ? store.name.contains(name) : null;
    }
}
