package com.foodwallet.server.domain.menu;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SellingStatus {

    SELLING("판매중"),
    HOLD("판매보류"),
    STOP_SELLING("판매중지");

    private final String text;

    public static SellingStatus of(String type) {
        for (SellingStatus value : values()) {
            if (value.toString().equals(type)) {
                return value;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 판매 상태 타입입니다.");
    }
}
