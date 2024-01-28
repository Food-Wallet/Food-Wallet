package com.foodwallet.server.domain.operation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OperationStatus {

    START("운영시작"),
    FINISH("운영종료");

    private final String text;
}
