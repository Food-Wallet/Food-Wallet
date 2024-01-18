package com.foodwallet.server.domain.member.repository;

import com.foodwallet.server.domain.member.Member;

public interface MemberRepository {

    Member save(Member member);

    Member findByEmail(String email);
}
