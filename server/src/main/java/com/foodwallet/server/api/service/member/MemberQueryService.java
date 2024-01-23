package com.foodwallet.server.api.service.member;

import com.foodwallet.server.api.service.member.response.CheckEmailDuplicationResponse;
import com.foodwallet.server.domain.member.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    /**
     * 이메일을 입력 받아 이메일 사용 여부를 확인한다.
     *
     * @param email 검증할 이메일
     * @return 이메일 사용 여부 검증 결과 정보
     */
    public CheckEmailDuplicationResponse checkEmailDuplication(String email) {
        boolean isDuplicated = memberQueryRepository.existEmail(email);

        return CheckEmailDuplicationResponse.builder()
            .isDuplicated(isDuplicated)
            .build();
    }
}
