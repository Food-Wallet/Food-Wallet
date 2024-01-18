package com.foodwallet.server.domain.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreStatus {

    OPEN("영업중"),
    REST("휴무"),
    CLOSE("영업종료");

    private final String text;
}
