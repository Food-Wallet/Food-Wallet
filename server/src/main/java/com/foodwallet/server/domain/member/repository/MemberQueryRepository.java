package com.foodwallet.server.domain.member.repository;

import com.foodwallet.server.api.service.member.response.MemberInfoResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import static com.foodwallet.server.domain.member.QMember.*;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public boolean existEmail(String email) {
        Integer content = queryFactory
            .selectOne()
            .from(member)
            .where(member.email.eq(email))
            .fetchFirst();
        return content != null;
    }

    public MemberInfoResponse findByEmail(String email) {
        return null;
    }
}
