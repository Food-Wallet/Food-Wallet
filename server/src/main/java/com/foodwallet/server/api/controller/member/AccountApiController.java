package com.foodwallet.server.api.controller.member;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.member.request.CheckEmailDuplicationRequest;
import com.foodwallet.server.api.controller.member.request.MemberCreateRequest;
import com.foodwallet.server.api.controller.member.request.SigninRequest;
import com.foodwallet.server.api.service.member.MemberQueryService;
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
    private final MemberQueryService memberQueryService;

    /**
     * 회원 가입 API
     *
     * @param request 신규 회원 등록을 위한 회원 정보
     * @return 등록된 신규 회원의 정보
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberCreateResponse> signup(@Valid @RequestBody MemberCreateRequest request) {
        LocalDate currentDate = LocalDate.now();

        MemberCreateResponse response = memberService.createMember(request.toServiceRequest(), currentDate);

        return ApiResponse.created(response);
    }

    /**
     * 이메일 중복 검사 API
     *
     * @param request 중복 검사할 이메일 정보
     * @return 이메일 중복 여부 결과 정보
     */
    @PostMapping("/email")
    public ApiResponse<CheckEmailDuplicationResponse> checkEmailDuplication(@Valid @RequestBody CheckEmailDuplicationRequest request) {

        CheckEmailDuplicationResponse response = memberQueryService.checkEmailDuplication(request.getEmail());

        return ApiResponse.ok(response);
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

    @PostMapping("/logout")
    public ApiResponse<String> logout() {
        return ApiResponse.ok(null);
    }
}
