package com.foodwallet.server.api.service.member;

import com.foodwallet.server.api.service.member.request.MemberCreateServiceRequest;
import com.foodwallet.server.api.service.member.response.MemberCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    public MemberCreateResponse createMember(MemberCreateServiceRequest request, LocalDate currentDate) {
        return null;
    }
}
