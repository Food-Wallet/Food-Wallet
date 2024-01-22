package com.foodwallet.server.api.service.member;

import com.foodwallet.server.api.service.member.request.MemberCreateServiceRequest;
import com.foodwallet.server.api.service.member.response.MemberCreateResponse;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberQueryRepository;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static com.foodwallet.server.common.constant.RegExpConst.*;
import static com.foodwallet.server.common.message.ErrorMessage.*;
import static com.foodwallet.server.common.message.ExceptionMessage.DUPLICATED_EMAIL;
import static com.foodwallet.server.common.message.ExceptionMessage.UNSUPPORTED_GENDER;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원 정보를 입력 받아 신규 회원을 등록한다.
     *
     * @param request     신규 회원의 정보
     * @param currentDate 신규 회원 등록을 요청한 날짜 정보
     * @return 신규 등록된 회원의 정보
     */
    public MemberCreateResponse createMember(MemberCreateServiceRequest request, LocalDate currentDate) {
        String validatedEmail = emailValidation(request.getEmail());

        String encodedPwd = passwordValidation(request.getPwd());

        String validatedName = nameValidation(request.getName());

        int validatedBirthYear = birthYearValidation(request.getBirthYear(), currentDate);

        String validatedGender = genderValidation(request.getGender());

        Member member = Member.create(validatedEmail, encodedPwd, validatedName, validatedBirthYear, validatedGender, request.getRole());
        Member savedMember = memberRepository.save(member);

        return MemberCreateResponse.of(savedMember);
    }

    /**
     * 이메일을 입력 받아 유효성 검증한다.
     *
     * @param email 검증할 이메일
     * @return 검증이 완료된 이메일
     * @throws IllegalArgumentException 다른 회원이 이메일을 사용중이거나 이메일 형식이 틀린 경우
     */
    private String emailValidation(String email) {
        boolean isExistEmail = memberQueryRepository.existEmail(email);

        if (isExistEmail) {
            throw new IllegalArgumentException(DUPLICATED_EMAIL);
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
    private String passwordValidation(String password) {
        if (password.length() < 8 || 20 < password.length()) {
            throw new IllegalArgumentException(SIZE_PASSWORD);
        }

        if (!Pattern.matches(REGEXP_PASSWORD, password)) {
            throw new IllegalArgumentException(NOT_MATCH_PATTERN_PASSWORD);
        }

        return passwordEncoder.encode(password);
    }

    /**
     * 이름을 입력 받아 유효성 검증한다.
     *
     * @param name 검증할 이름
     * @return 검증이 완료된 이름
     * @throws IllegalArgumentException 이름의 형식이 틀린 경우
     */
    private String nameValidation(String name) {
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
    private int birthYearValidation(int birthYear, LocalDate currentDate) {
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
    private String genderValidation(String gender) {
        if (!(gender.equals("M") || gender.equals("F"))) {
            throw new IllegalArgumentException(UNSUPPORTED_GENDER);
        }

        return gender;
    }
}
