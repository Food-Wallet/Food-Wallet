package com.foodwallet.server.domain.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreType {

    CHICKEN("치킨"),
    SNACK("간식");

    private final String text;
}
