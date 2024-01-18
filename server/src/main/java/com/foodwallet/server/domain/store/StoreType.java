package com.foodwallet.server.domain.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreType {

    CHICKEN("치킨"),
    SNACK("간식");

    private final String text;

    public static StoreType of(String type) {
        for (StoreType value : values()) {
            if (value.toString().equals(type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 매장 타입입니다.");
    }
}
