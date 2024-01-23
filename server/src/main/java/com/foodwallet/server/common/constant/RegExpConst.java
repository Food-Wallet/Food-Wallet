package com.foodwallet.server.common.constant;

public abstract class RegExpConst {

    public static final String REGEXP_EMAIL = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$";
    public static final String REGEXP_PASSWORD = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";
    public static final String REGEXP_NAME = "^[가-힣]*$";

}
