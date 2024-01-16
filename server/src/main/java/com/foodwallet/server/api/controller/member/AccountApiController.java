package com.foodwallet.server.api.controller.member;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.member.request.MemberCreateRequest;
import com.foodwallet.server.api.service.member.response.MemberCreateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AccountApiController {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberCreateResponse> signup(@Valid @RequestBody MemberCreateRequest request) {
        MemberCreateResponse response = MemberCreateResponse.builder()
            .email("dong82@naver.com")
            .name("동팔이")
            .signupDateTime(LocalDateTime.of(2024, 1, 16, 9, 0))
            .build();
        return ApiResponse.created(response);
    }
}
