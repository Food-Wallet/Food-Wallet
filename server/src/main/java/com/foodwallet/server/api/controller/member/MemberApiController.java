package com.foodwallet.server.api.controller.member;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.controller.member.request.PwdModifyRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberApiController {

    @PatchMapping("/pwd")
    public ApiResponse<String> modifyPwd(@Valid @RequestBody PwdModifyRequest request) {
        return ApiResponse.ok(null);
    }
}
