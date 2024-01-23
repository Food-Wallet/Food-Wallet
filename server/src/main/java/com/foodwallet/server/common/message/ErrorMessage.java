package com.foodwallet.server.common.message;

/**
 * Binding 오류 메세지 상수
 */
public abstract class ErrorMessage {

    public static final String NOT_BLANK_EMAIL = "이메일을 입력하세요.";
    public static final String NOT_BLANK_PASSWORD = "비밀번호를 입력하세요.";
    public static final String NOT_BLANK_NAME = "이름을 입력하세요.";
    public static final String POSITIVE_BIRTH_YEAR = "출생연도를 정확하게 입력하세요.";
    public static final String NOT_BLANK_GENDER = "성별을 입력하세요.";
    public static final String NOT_BLANK_ROLE = "회원 구분을 입력하세요.";
    public static final String NOT_MATCH_PATTERN_EMAIL = "이메일을 올바르게 입력해주세요.";
    public static final String SIZE_PASSWORD = "비밀번호의 길이는 8자 이상 20자 이하입니다.";
    public static final String NOT_MATCH_PATTERN_PASSWORD = "비밀번호를 올바르게 입력해주세요.";
    public static final String NOT_MATCH_PATTERN_NAME = "이름을 올바르게 입력해주세요.";
    public static final String FUTURE_BIRTH_YEAR = "출생연도를 올바르게 입력해주세요.";
    public static final String SIZE_EMAIL = "이메일의 길이는 최대 100자입니다.";
    public static final String SIZE_NAME = "이름의 길이는 최대 20자입니다.";
    public static final String UNSUPPORTED_BIRTH_YEAR = "1900년생부터 가입이 가능합니다.";
    public static final String NOT_BLANK_FCM_TOKEN = "FCM 토큰을 입력하세요.";
    public static final String NOT_BLANK_BANK_CODE = "은행 코드를 입력하세요.";
    public static final String NOT_BLANK_ACCOUNT_NUMBER = "계좌 번호를 입력하세요.";
    public static final String NOT_BLANK_ACCOUNT_PWD = "계좌 비밀번호를 입력하세요.";

    public static final String NOT_BLANK_STORE_TYPE = "매장 타입은 필수입니다.";
    public static final String NOT_BLANK_STORE_NAME = "매장명은 필수입니다.";
    public static final String NOT_BLANK_STORE_ADDRESS = "주소는 필수입니다.";
    public static final String NOT_BLANK_STORE_OPEN_TIME = "운영 시간은 필수입니다.";
    public static final String NOT_NULL_STORE_LATITUDE = "위도는 필수입니다.";
    public static final String NOT_NULL_STORE_LONGITUDE = "경도는 필수입니다.";

    public static final String NOT_BLANK_MENU_NAME = "메뉴명은 필수입니다.";
    public static final String POSITIVE_MENU_PRICE = "메뉴 가격은 양수여야 합니다.";
    public static final String NOT_BLANK_MENU_SELLING_STATUS = "판매 상태는 필수입니다.";
}
