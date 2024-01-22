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

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberCreateResponse createMember(MemberCreateServiceRequest request, LocalDate currentDate) {
        //이메일 중복 검사
        boolean isExistEmail = memberQueryRepository.existEmail(request.getEmail());
        if (isExistEmail) {
            throw new IllegalArgumentException("이미 가입된 이메일 주소입니다.");
        }

        //이메일 형식 검사
        if (!Pattern.matches("^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$", request.getEmail())) {
            throw new IllegalArgumentException("이메일을 올바르게 입력해주세요.");
        }

        //비밀번호 길이 검사
        if (request.getPwd().length() < 8 || 20 < request.getPwd().length()) {
            throw new IllegalArgumentException("비밀번호의 길이는 8자 이상 20자 이하입니다.");
        }

        //비밀번호 형식 검사
        if (!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", request.getPwd())) {
            throw new IllegalArgumentException("비밀번호를 올바르게 입력해주세요.");
        }

        //이름 형식 검사
        if (!Pattern.matches("^[가-힣]*$", request.getName())) {
            throw new IllegalArgumentException("이름을 올바르게 입력해주세요.");
        }

        //출생연도 검사
        LocalDate birthDate = LocalDate.of(request.getBirthYear(), 1, 1);
        if (currentDate.isBefore(birthDate)) {
            throw new IllegalArgumentException("출생연도를 올바르게 입력해주세요.");
        }

        //성별검사
        if (!(request.getGender().equals("M") || request.getGender().equals("F"))) {
            throw new IllegalArgumentException("성별을 올바르게 입력해주세요.");
        }

        String encodedPwd = passwordEncoder.encode(request.getPwd());

        Member member = Member.create(request.getEmail(), encodedPwd, request.getName(), request.getBirthYear(), request.getGender(), request.getRole());
        Member savedMember = memberRepository.save(member);

        return MemberCreateResponse.of(savedMember);
    }
}
