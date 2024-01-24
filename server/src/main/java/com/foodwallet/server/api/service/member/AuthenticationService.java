package com.foodwallet.server.api.service.member;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.api.service.member.request.ConnectAccountServiceRequest;
import com.foodwallet.server.api.service.member.response.ConnectAccountResponse;
import com.foodwallet.server.client.VirtualBankClient;
import com.foodwallet.server.client.request.OneTransferRequest;
import com.foodwallet.server.client.response.OneTransferResponse;
import com.foodwallet.server.domain.member.Account;
import com.foodwallet.server.domain.member.ConnectAccount;
import com.foodwallet.server.domain.member.repository.ConnectAccountRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

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
        return null;
    }

    private String generateAuthenticationNumber(int size) {
        int bound = (int) Math.pow(10, size);

        Random random = new Random();
        int number = random.nextInt(bound);
        String format = "%0" + size + "d";
        return String.format(format, number);
    }
}
