package com.foodwallet.server.domain.menu.repository;

import com.foodwallet.server.domain.menu.repository.response.MenuResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MenuQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<MenuResponse> findByStoreId(Long storeId) {
        return null;
    }
}
