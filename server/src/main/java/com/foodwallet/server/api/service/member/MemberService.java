package com.foodwallet.server.api.service.member;

import com.foodwallet.server.api.service.member.request.MemberCreateServiceRequest;
import com.foodwallet.server.api.service.member.response.MemberCreateResponse;
import com.foodwallet.server.api.service.member.response.MemberWithdrawalResponse;
import com.foodwallet.server.api.service.member.response.PwdModifyResponse;
import com.foodwallet.server.common.exception.AuthenticationException;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberQueryRepository;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.foodwallet.server.api.service.member.MemberValidator.*;
import static com.foodwallet.server.common.message.ExceptionMessage.DUPLICATED_EMAIL;
import static com.foodwallet.server.common.message.ExceptionMessage.NOT_AUTHORIZED;

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
        boolean isExistEmail = memberQueryRepository.existEmail(request.getEmail());

        if (isExistEmail) {
            throw new IllegalArgumentException(DUPLICATED_EMAIL);
        }

        String validatedEmail = emailValidation(request.getEmail());

        String validatedPwd = passwordValidation(request.getPwd());

        String validatedName = nameValidation(request.getName());

        int validatedBirthYear = birthYearValidation(request.getBirthYear(), currentDate);

        String validatedGender = genderValidation(request.getGender());

        String encodedPwd = passwordEncoder.encode(validatedPwd);
        Member member = Member.create(validatedEmail, encodedPwd, validatedName, validatedBirthYear, validatedGender, request.getRole());
        Member savedMember = memberRepository.save(member);

        return MemberCreateResponse.of(savedMember);
    }

    public PwdModifyResponse modifyPwd(String email, String currentPwd, String newPwd) {
        Member member = memberRepository.findByEmail(email);

        boolean isMatches = passwordEncoder.matches(currentPwd, member.getPwd());
        if (!isMatches) {
            throw new AuthenticationException(NOT_AUTHORIZED);
        }

        String validatedPwd = passwordValidation(newPwd);
        String encodedPwd = passwordEncoder.encode(validatedPwd);
        member.modifyPwd(encodedPwd);

        return PwdModifyResponse.of(member);
    }

    public MemberWithdrawalResponse removeMember(String email, String currentPwd) {
        return null;
    }
}
