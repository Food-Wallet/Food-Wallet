package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.operation.Operation;
import com.foodwallet.server.domain.store.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreCloseResponse {

    private final Long storeId;
    private final String status;
    private final String storeName;
    private final OperationCloseResponse operationInfo;
    private final LocalDateTime finishedDateTime;

    @Builder
    private StoreCloseResponse(Long storeId, String status, String storeName, OperationCloseResponse operationInfo, LocalDateTime finishedDateTime) {
        this.storeId = storeId;
        this.status = status;
        this.storeName = storeName;
        this.operationInfo = operationInfo;
        this.finishedDateTime = finishedDateTime;
    }

    public static StoreCloseResponse of(Store store, Operation operation, LocalDateTime currentDateTime) {
        return StoreCloseResponse.builder()
            .storeId(store.getId())
            .status(store.getStatus().getText())
            .storeName(store.getName())
            .operationInfo(OperationCloseResponse.of(operation))
            .finishedDateTime(currentDateTime)
            .build();
    }
}
