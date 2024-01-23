package com.foodwallet.server.api.controller.member;

import com.foodwallet.server.ControllerTestSupport;
import com.foodwallet.server.api.controller.member.request.CheckEmailDuplicationRequest;
import com.foodwallet.server.api.controller.member.request.LoginRequest;
import com.foodwallet.server.api.controller.member.request.MemberCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.foodwallet.server.domain.member.MemberRole.USER;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountApiControllerTest extends ControllerTestSupport {

    private static final String BASE_URL = "/api/v1/auth";

    @DisplayName("신규 회원을 등록한다.")
    @Test
    void signup() throws Exception {
        //given
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(USER)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/signup")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @DisplayName("신규 회원을 등록할 때 이메일은 필수값이다.")
    @Test
    void signupWithoutEmail() throws Exception {
        //given
        MemberCreateRequest request = MemberCreateRequest.builder()
            .pwd("dong1234!")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(USER)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/signup")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이메일을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 회원을 등록할 때 비밀번호는 필수값이다.")
    @Test
    void signupWithoutPwd() throws Exception {
        //given
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("dong82@naver.com")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .role(USER)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/signup")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("비밀번호를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 회원을 등록할 때 이름은 필수값이다.")
    @Test
    void signupWithoutName() throws Exception {
        //given
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .birthYear(2015)
            .gender("F")
            .role(USER)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/signup")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이름을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 회원을 등록할 때 출생연도는 양수이다.")
    @Test
    void signupWithBirthYearZero() throws Exception {
        //given
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("동팔이")
            .birthYear(0)
            .gender("F")
            .role(USER)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/signup")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("출생연도를 정확하게 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 회원을 등록할 때 성별은 필수값이다.")
    @Test
    void signupWithoutGender() throws Exception {
        //given
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("동팔이")
            .birthYear(2015)
            .role(USER)
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/signup")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("성별을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 회원을 등록할 때 회원 구분은 필수값이다.")
    @Test
    void signupWithoutRole() throws Exception {
        //given
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .name("동팔이")
            .birthYear(2015)
            .gender("F")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/signup")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("회원 구분을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("이메일 중복 체크를 한다.")
    @Test
    void checkEmailDuplication() throws Exception {
        //given
        CheckEmailDuplicationRequest request = CheckEmailDuplicationRequest.builder()
            .email("dong82@naver.com")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/email")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("이메일 중복 체크할 때 이메일은 필수값이다.")
    @Test
    void checkEmailDuplicationWithoutEmail() throws Exception {
        //given
        CheckEmailDuplicationRequest request = CheckEmailDuplicationRequest.builder()
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/email")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이메일을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("계정 로그인을 한다.")
    @Test
    void login() throws Exception {
        //given
        LoginRequest request = LoginRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .fcmToken("fcm.token")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/login")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }

    @DisplayName("로그인할 때 이메일은 필수값이다.")
    @Test
    void loginWithoutEmail() throws Exception {
        //given
        LoginRequest request = LoginRequest.builder()
            .pwd("dong1234!")
            .fcmToken("fcm.token")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/login")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("이메일을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("로그인할 때 비밀번호는 필수값이다.")
    @Test
    void loginWithoutPwd() throws Exception {
        //given
        LoginRequest request = LoginRequest.builder()
            .email("dong82@naver.com")
            .fcmToken("fcm.token")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/login")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("비밀번호를 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("로그인할 때 FCM 토큰은 필수값이다.")
    @Test
    void loginWithoutFcmToken() throws Exception {
        //given
        LoginRequest request = LoginRequest.builder()
            .email("dong82@naver.com")
            .pwd("dong1234!")
            .build();

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/login")
                    .with(csrf())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("FCM 토큰을 입력하세요."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("계정 로그아웃을 한다.")
    @Test
    void logout() throws Exception {
        //given

        //when //then
        mockMvc.perform(
                post(BASE_URL + "/logout")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk());
    }
}