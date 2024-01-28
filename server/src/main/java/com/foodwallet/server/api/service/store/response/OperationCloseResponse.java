package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.operation.Operation;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OperationCloseResponse {

    private final Long operationId;
    private final String address;
    private final String time;
    private final double latitude;
    private final double longitude;

    @Builder
    private OperationCloseResponse(Long operationId, String address, String time, double latitude, double longitude) {
        this.operationId = operationId;
        this.address = address;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static OperationCloseResponse of(Operation operation) {
        return OperationCloseResponse.builder()
            .operationId(operation.getId())
            .address(operation.getAddress())
            .time(operation.getTime())
            .latitude(operation.getCoordinate().getLatitude())
            .longitude(operation.getCoordinate().getLongitude())
            .build();
    }
}
