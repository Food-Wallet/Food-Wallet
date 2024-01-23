package com.foodwallet.server.api.service.member;

import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import com.foodwallet.server.security.JwtTokenProvider;
import com.foodwallet.server.security.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AccountService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenInfo login(String email, String pwd, String fcmToken) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, pwd);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        updateToken(email, fcmToken, tokenInfo.getRefreshToken());

        return tokenInfo;
    }

    public void logout(String email) {
        Member member = memberRepository.findByEmail(email);

        member.removeToken();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Member member = memberRepository.findByEmail(email);

        return createMemberUserDetails(member);
    }

    private UserDetails createMemberUserDetails(Member member) {
        return User.builder()
            .username(member.getEmail())
            .password(member.getPwd())
            .roles(member.getRole().toString())
            .build();
    }

    private void updateToken(String email, String fcmToken, String refreshToken) {
        Member member = memberRepository.findByEmail(email);

        member.modifyToken(fcmToken, refreshToken);
    }
}
