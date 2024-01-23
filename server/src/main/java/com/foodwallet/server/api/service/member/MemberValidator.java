package com.foodwallet.server.api.service.member;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import static com.foodwallet.server.common.message.ErrorMessage.*;
import static com.foodwallet.server.common.message.ExceptionMessage.UNSUPPORTED_GENDER;

public abstract class MemberValidator {

    private static final int MAX_EMAIL_LENGTH = 100;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MIN_BIRTH_YEAR = 1900;
    private static final List<String> GENDER = List.of("M", "F");

    private static final String REGEXP_EMAIL = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$";
    private static final String REGEXP_PASSWORD = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";
    private static final String REGEXP_NAME = "^[가-힣]*$";

    /**
     * 이메일을 입력 받아 유효성 검증한다.
     *
     * @param email 검증할 이메일
     * @return 검증이 완료된 이메일
     * @throws IllegalArgumentException 다른 회원이 이메일을 사용중이거나 이메일 형식이 틀린 경우
     */
    public static String emailValidation(String email) {
        if (MAX_EMAIL_LENGTH < email.length()) {
            throw new IllegalArgumentException(SIZE_EMAIL);
        }

        if (!Pattern.matches(REGEXP_EMAIL, email)) {
            throw new IllegalArgumentException(NOT_MATCH_PATTERN_EMAIL);
        }

        return email;
    }

    /**
     * 비밀번호를 입력 받아 유효성 검증한다.
     *
     * @param password 검증할 비밀번호
     * @return 검증이 완료된 비밀번호
     * @throws IllegalArgumentException 비밀번호 길이를 벗어나거나 형식이 틀린 경우
     */
    public static String passwordValidation(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH || MAX_PASSWORD_LENGTH < password.length()) {
            throw new IllegalArgumentException(SIZE_PASSWORD);
        }

        if (!Pattern.matches(REGEXP_PASSWORD, password)) {
            throw new IllegalArgumentException(NOT_MATCH_PATTERN_PASSWORD);
        }

        return password;
    }

    /**
     * 이름을 입력 받아 유효성 검증한다.
     *
     * @param name 검증할 이름
     * @return 검증이 완료된 이름
     * @throws IllegalArgumentException 이름의 형식이 틀린 경우
     */
    public static String nameValidation(String name) {
        if (MAX_NAME_LENGTH < name.length()) {
            throw new IllegalArgumentException(SIZE_NAME);
        }

        if (!Pattern.matches(REGEXP_NAME, name)) {
            throw new IllegalArgumentException(NOT_MATCH_PATTERN_NAME);
        }

        return name;
    }

    /**
     * 출생연도와 현재 날짜를 입력 받아 유효성 검증한다.
     *
     * @param birthYear   검증할 출생연도
     * @param currentDate 현재 날짜
     * @return 검증이 완료된 출생연도
     * @throws IllegalArgumentException 출생연도가 미래인 경우
     */
    public static int birthYearValidation(int birthYear, LocalDate currentDate) {
        if (birthYear < MIN_BIRTH_YEAR) {
            throw new IllegalArgumentException(UNSUPPORTED_BIRTH_YEAR);
        }

        LocalDate birthDate = LocalDate.of(birthYear, 1, 1);
        if (currentDate.isBefore(birthDate)) {
            throw new IllegalArgumentException(FUTURE_BIRTH_YEAR);
        }

        return birthYear;
    }

    /**
     * 성별을 입력 받아 유효성 검증한다.
     *
     * @param gender 검증할 성별
     * @return 검증이 완료된 성별
     * @throws IllegalArgumentException 서비스에서 지원하는 성별이 아닌 경우
     */
    public static String genderValidation(String gender) {
        if (!GENDER.contains(gender)) {
            throw new IllegalArgumentException(UNSUPPORTED_GENDER);
        }

        return gender;
    }
}
