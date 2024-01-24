package com.foodwallet.server.api.controller.member;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.member.request.ConnectAccountRequest;
import com.foodwallet.server.api.controller.member.request.MatchAuthenticationNumberRequest;
import com.foodwallet.server.api.controller.member.request.MemberWithdrawalRequest;
import com.foodwallet.server.api.controller.member.request.PwdModifyRequest;
import com.foodwallet.server.api.service.member.AuthenticationService;
import com.foodwallet.server.api.service.member.MemberQueryService;
import com.foodwallet.server.api.service.member.MemberService;
import com.foodwallet.server.api.service.member.response.ConnectAccountResponse;
import com.foodwallet.server.api.service.member.response.MemberInfoResponse;
import com.foodwallet.server.api.service.member.response.MemberWithdrawalResponse;
import com.foodwallet.server.api.service.member.response.PwdModifyResponse;
import com.foodwallet.server.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberApiController {

    private final AuthenticationService authenticationService;
    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    @GetMapping
    public ApiResponse<MemberInfoResponse> searchMemberInfo() {
        String email = SecurityUtils.getCurrentEmail();

        MemberInfoResponse response = memberQueryService.searchMemberInfo(email);

        return ApiResponse.ok(response);
    }

    @PostMapping("/account")
    public ApiResponse<ConnectAccountResponse> connectAccount(@Valid @RequestBody ConnectAccountRequest request) {
        String email = SecurityUtils.getCurrentEmail();

        ConnectAccountResponse response = authenticationService.connectAccount(email, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PostMapping("/account/match")
    public ApiResponse<ConnectAccountResponse> matchAuthenticationNumber(@Valid @RequestBody MatchAuthenticationNumberRequest request) {
        String email = SecurityUtils.getCurrentEmail();

        ConnectAccountResponse response = authenticationService.matchAuthenticationNumber(email, request.getAuthenticationNumber());

        return ApiResponse.ok(response);
    }

    @PatchMapping("/pwd")
    public ApiResponse<PwdModifyResponse> modifyPwd(@Valid @RequestBody PwdModifyRequest request) {
        String email = SecurityUtils.getCurrentEmail();

        PwdModifyResponse response = memberService.modifyPwd(email, request.getCurrentPwd(), request.getNewPwd());

        return ApiResponse.ok(response);
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
