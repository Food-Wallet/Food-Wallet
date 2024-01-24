package com.foodwallet.server.domain.member.repository;

import com.foodwallet.server.api.service.member.response.MemberInfoResponse;
import com.querydsl.core.types.Projections;
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
        return queryFactory
            .select(
                Projections.constructor(MemberInfoResponse.class,
                    member.email,
                    member.name,
                    member.birthYear,
                    member.gender,
                    member.role,
                    member.account.bankCode,
                    member.account.accountNumber
                )
            )
            .from(member)
            .where(
                member.isDeleted.isFalse(),
                member.email.eq(email)
            )
            .fetchFirst();
    }
}
