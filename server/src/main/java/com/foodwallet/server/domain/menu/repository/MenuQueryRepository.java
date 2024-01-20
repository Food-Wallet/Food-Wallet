package com.foodwallet.server.domain.menu.repository;

import com.foodwallet.server.domain.menu.repository.response.MenuResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.foodwallet.server.domain.menu.QMenu.menu;
import static com.foodwallet.server.domain.menu.SellingStatus.HOLD;
import static com.foodwallet.server.domain.menu.SellingStatus.SELLING;

@Repository
public class MenuQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MenuQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<MenuResponse> findByStoreId(Long storeId) {
        List<MenuResponse> content = queryFactory
            .select(
                Projections.constructor(MenuResponse.class,
                    menu.id,
                    menu.name,
                    menu.description,
                    menu.price,
                    menu.status,
                    menu.image.uploadFileName
                )
            )
            .from(menu)
            .where(
                menu.isDeleted.isFalse(),
                menu.store.id.eq(storeId),
                menu.status.in(List.of(SELLING, HOLD))
            )
            .fetch();
        return content;
    }
}
