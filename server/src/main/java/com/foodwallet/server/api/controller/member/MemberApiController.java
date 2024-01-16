package com.foodwallet.server.api.controller.member;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.member.request.MemberWithdrawalRequest;
import com.foodwallet.server.api.controller.member.request.PwdModifyRequest;
import com.foodwallet.server.api.service.member.response.MemberWithdrawalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberApiController {

    @PatchMapping("/pwd")
    public ApiResponse<String> modifyPwd(@Valid @RequestBody PwdModifyRequest request) {
        return ApiResponse.ok(null);
    }

    @PatchMapping("/withdrawal")
    public ApiResponse<MemberWithdrawalResponse> withdrawal(@Valid @RequestBody MemberWithdrawalRequest request) {
        MemberWithdrawalResponse response = MemberWithdrawalResponse.builder()
            .email("dong82@naver.com")
            .name("동팔이")
            .withdrawalDateTime(LocalDateTime.of(2024, 1, 16, 18, 0))
            .build();
        return ApiResponse.ok(response);
    }
}
