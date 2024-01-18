package com.foodwallet.server.domain.member.repository.jpa;

import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Member findByEmail(String email) {
        Optional<Member> findMember = memberJpaRepository.findByEmail(email);
        if (findMember.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }
        return findMember.get();
    }
}
