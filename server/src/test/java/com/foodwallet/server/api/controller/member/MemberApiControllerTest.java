package com.foodwallet.server.api.controller.member;

import com.foodwallet.server.ControllerTestSupport;
import com.foodwallet.server.api.controller.member.request.ConnectAccountRequest;
import com.foodwallet.server.api.controller.member.request.MatchAuthenticationNumberRequest;
import com.foodwallet.server.api.controller.member.request.MemberWithdrawalRequest;
import com.foodwallet.server.api.controller.member.request.PwdModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/members";

    @DisplayName("1원 이체로 인증 번호를 발급한다.")
    @Test
    void connectAccount() throws Exception {
        //given
        ConnectAccountRequest request = ConnectAccountRequest.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .accountPwd("1234")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/account")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("인증 번호 발급시 은행 코드는 필수값이다.")
    @Test
    void connectAccountWithoutBankCode() throws Exception {
        //given
        ConnectAccountRequest request = ConnectAccountRequest.builder()
            .accountNumber("110111222222")
            .accountPwd("1234")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/account")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("은행 코드를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("인증 번호 발급시 계좌 번호는 필수값이다.")
    @Test
    void connectAccountWithoutAccountNumber() throws Exception {
        //given
        ConnectAccountRequest request = ConnectAccountRequest.builder()
            .bankCode("088")
            .accountPwd("1234")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/account")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("계좌 번호를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("인증 번호 발급시 계좌 비밀번호는 필수값이다.")
    @Test
    void connectAccountWithoutAccountPwd() throws Exception {
        //given
        ConnectAccountRequest request = ConnectAccountRequest.builder()
            .bankCode("088")
            .accountNumber("110111222222")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/account")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("계좌 비밀번호를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("발급 받은 인증 번호 일치 여부를 확인한다.")
    @Test
    void matchAuthenticationNumber() throws Exception {
        //given
        MatchAuthenticationNumberRequest request = MatchAuthenticationNumberRequest.builder()
            .authenticationNumber("1234")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/account/match")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("인증 번호 일치 여부를 확인시 인증 번호는 필수값이다.")
    @Test
    void matchAuthenticationNumberWithoutAuthenticationNumber() throws Exception {
        //given
        MatchAuthenticationNumberRequest request = MatchAuthenticationNumberRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/account/match")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("인증 번호를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void modifyPwd() throws Exception {
        //given
        PwdModifyRequest request = PwdModifyRequest.builder()
            .currentPwd("dong1234!")
            .newPwd("dong8282@")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/pwd")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("비밀번호 수정시 현재 비밀번호는 필수값이다.")
    @Test
    void modifyPwdWithoutCurrentPwd() throws Exception {
        //given
        PwdModifyRequest request = PwdModifyRequest.builder()
            .newPwd("dong8282@")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/pwd")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("현재 비밀번호를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("비밀번호 수정시 새로운 비밀번호는 필수값이다.")
    @Test
    void modifyPwdWithoutNewPwd() throws Exception {
        //given
        PwdModifyRequest request = PwdModifyRequest.builder()
            .currentPwd("dong1234!")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/pwd")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("새로운 비밀번호를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("회원 탈퇴를 한다.")
    @Test
    void withdrawal() throws Exception {
        //given
        MemberWithdrawalRequest request = MemberWithdrawalRequest.builder()
            .pwd("dong1234!")
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/withdrawal")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("회원 탈퇴시 현재 비밀번호는 필수값이다.")
    @Test
    void withdrawalWithoutPwd() throws Exception {
        //given
        MemberWithdrawalRequest request = MemberWithdrawalRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                patch(BASE_URL + "/withdrawal")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("현재 비밀번호를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }
}