package com.foodwallet.server.api.service.member;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.service.member.request.ConnectAccountServiceRequest;
import com.foodwallet.server.api.service.member.response.ConnectAccountResponse;
import com.foodwallet.server.client.VirtualBankClient;
import com.foodwallet.server.client.request.OneTransferRequest;
import com.foodwallet.server.client.response.OneTransferResponse;
import com.foodwallet.server.domain.member.Account;
import com.foodwallet.server.domain.member.ConnectAccount;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.member.repository.ConnectAccountRedisRepository;
import com.foodwallet.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final MemberRepository memberRepository;
    private final ConnectAccountRedisRepository connectAccountRedisRepository;
    private final VirtualBankClient virtualBankClient;

    public ConnectAccountResponse connectAccount(String email, ConnectAccountServiceRequest request) {
        String authenticationNumber = generateAuthenticationNumber(4);

        Account account = Account.builder()
            .bankCode(request.getBankCode())
            .accountNumber(request.getAccountNumber())
            .accountPwd(request.getAccountPwd())
            .build();

        ConnectAccount connectAccount = ConnectAccount.builder()
            .email(email)
            .account(account)
            .authenticationNumber(authenticationNumber)
            .build();

        OneTransferRequest clientRequest = OneTransferRequest.builder()
            .bankCode(request.getBankCode())
            .accountNumber(request.getAccountNumber())
            .message(authenticationNumber)
            .build();

        ApiResponse<OneTransferResponse> clientResponse = virtualBankClient.oneTransfer(clientRequest);

        ConnectAccount savedConnectAccount = connectAccountRedisRepository.save(connectAccount);

        return ConnectAccountResponse.of(savedConnectAccount);
    }

    public ConnectAccountResponse matchAuthenticationNumber(String email, String authenticationNumber) {
        Optional<ConnectAccount> findConnectAccount = connectAccountRedisRepository.findById(email);
        if (findConnectAccount.isEmpty()) {
            throw new IllegalArgumentException("유효 시간이 만료되었습니다.");
        }
        ConnectAccount connectAccount = findConnectAccount.get();

        if (!connectAccount.getAuthenticationNumber().equals(authenticationNumber)) {
            throw new IllegalArgumentException("인증 번호를 확인해주세요.");
        }

        Member member = memberRepository.findByEmail(email);

        Account account = connectAccount.getAccount();
        member.modifyAccount(account);

        connectAccountRedisRepository.deleteById(email);

        return ConnectAccountResponse.of(connectAccount);
    }

    private String generateAuthenticationNumber(int size) {
        int bound = (int) Math.pow(10, size);

        Random random = new Random();
        int number = random.nextInt(bound);
        String format = "%0" + size + "d";
        return String.format(format, number);
    }
}
