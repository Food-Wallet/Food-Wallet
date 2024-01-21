package com.foodwallet.server.common.message;

/**
 * Binding 오류 메세지 상수
 */
public abstract class ErrorMessage {

    public static final String NOT_BLANK_STORE_TYPE = "매장 타입은 필수입니다.";
    public static final String NOT_BLANK_STORE_NAME = "매장명은 필수입니다.";
    public static final String NOT_BLANK_STORE_ADDRESS = "주소는 필수입니다.";
    public static final String NOT_BLANK_STORE_OPEN_TIME = "운영 시간은 필수입니다.";
    public static final String NOT_NULL_STORE_LATITUDE = "위도는 필수입니다.";
    public static final String NOT_NULL_STORE_LONGITUDE = "경도는 필수입니다.";
}
