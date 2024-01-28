package com.foodwallet.server.domain.operation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OperationStatus {

    OPEN("영업중"),
    CLOSE("영업종료");

    private final String text;
}
