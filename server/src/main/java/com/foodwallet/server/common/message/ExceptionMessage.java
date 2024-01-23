package com.foodwallet.server.common.message;

public abstract class ExceptionMessage {

    public static final String DUPLICATED_EMAIL = "이미 가입된 이메일 주소입니다.";
    public static final String UNSUPPORTED_GENDER = "성별을 올바르게 입력해주세요.";

    public static final String NOT_AUTHORIZED = "접근 권한이 없습니다.";
    public static final String IS_NOT_BUSINESS_MEMBER = "사업자 회원만 매장을 등록할 수 있습니다.";
    public static final String NO_ACCOUNT_INFORMATION = "매장을 등록하기 위해서 계좌를 등록해야 합니다.";

}
