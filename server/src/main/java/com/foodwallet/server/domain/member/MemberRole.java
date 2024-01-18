package com.foodwallet.server.domain.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    USER("ROLE_USER"),
    BUSINESS("ROLE_BUSINESS"),
    ADMIN("ROLE_ADMIN");

    private final String text;
}
