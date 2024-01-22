package com.foodwallet.server.api.controller.member;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.member.request.CheckEmailDuplicationRequest;
import com.foodwallet.server.api.controller.member.request.MemberCreateRequest;
import com.foodwallet.server.api.controller.member.request.SigninRequest;
import com.foodwallet.server.api.service.member.MemberService;
import com.foodwallet.server.api.service.member.response.CheckEmailDuplicationResponse;
import com.foodwallet.server.api.service.member.response.MemberCreateResponse;
import com.foodwallet.server.security.TokenInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AccountApiController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberCreateResponse> signup(@Valid @RequestBody MemberCreateRequest request) {
        LocalDate currentDate = LocalDate.now();

        MemberCreateResponse response = memberService.createMember(request.toServiceRequest(), currentDate);

        return ApiResponse.created(response);
    }

    @PostMapping("/signin")
    public ApiResponse<TokenInfo> signin(@Valid @RequestBody SigninRequest request) {
        TokenInfo tokenInfo = TokenInfo.builder()
            .grantType("Bearer")
            .accessToken("jwt.access.token")
            .refreshToken("jwt.refresh.token")
            .build();
        return ApiResponse.ok(tokenInfo);
    }

    @PostMapping("/email")
    public ApiResponse<CheckEmailDuplicationResponse> checkEmailDuplication(@Valid @RequestBody CheckEmailDuplicationRequest request) {
        CheckEmailDuplicationResponse response = CheckEmailDuplicationResponse.builder()
            .isDuplicated(true)
            .build();
        return ApiResponse.ok(response);
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout() {
        return ApiResponse.ok(null);
    }
}
