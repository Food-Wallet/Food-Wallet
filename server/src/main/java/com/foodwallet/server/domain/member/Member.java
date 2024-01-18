package com.foodwallet.server.domain.member;

import com.foodwallet.server.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.domain.member.MemberRole.BUSINESS;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false, updatable = false, length = 100)
    private String email;

    @Column(nullable = false, columnDefinition = "char(60)", length = 60)
    private String pwd;

    @Column(nullable = false, updatable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false, updatable = false, columnDefinition = "char(1)", length = 1)
    private String gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, columnDefinition = "varchar(10) default 'USER'", length = 10)
    private MemberRole role;

    @Embedded
    private Account account;

    @Embedded
    private Token token;

    @Builder
    private Member(String email, String pwd, String name, int age, String gender, MemberRole role, Account account, Token token) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.role = role;
        this.account = account;
        this.token = token;
    }

    public boolean isBusinessMember() {
        return role == BUSINESS;
    }

    public boolean isExistAccount() {
        return account != null;
    }
}
