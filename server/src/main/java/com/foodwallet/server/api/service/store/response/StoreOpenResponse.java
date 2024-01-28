package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.operation.Operation;
import com.foodwallet.server.domain.store.Store;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreOpenResponse {

    private final Long storeId;
    private final String status;
    private final String storeName;
    private final OperationOpenResponse operationInfo;
    private final LocalDateTime startedDateTime;

    @Builder
    private StoreOpenResponse(Long storeId, String status, String storeName, OperationOpenResponse operationInfo, LocalDateTime startedDateTime) {
        this.storeId = storeId;
        this.status = status;
        this.storeName = storeName;
        this.operationInfo = operationInfo;
        this.startedDateTime = startedDateTime;
    }

    public static StoreOpenResponse of(Store store, Operation operation) {
        return StoreOpenResponse.builder()
            .storeId(store.getId())
            .status(store.getStatus().getText())
            .storeName(store.getName())
            .operationInfo(OperationOpenResponse.of(operation))
            .startedDateTime(LocalDateTime.now())
            .build();
    }
}
