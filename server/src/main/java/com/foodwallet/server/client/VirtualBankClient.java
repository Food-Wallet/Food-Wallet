package com.foodwallet.server.client;

import com.foodwallet.server.api.ApiResponse;
import com.foodwallet.server.client.request.OneTransferRequest;
import com.foodwallet.server.client.response.OneTransferResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "virtual-bank", url = "http://localhost:8081")
public interface VirtualBankClient {

    @PostMapping("/api/v1/transfer")
    ApiResponse<OneTransferResponse> oneTransfer(@RequestBody OneTransferRequest request);
}
