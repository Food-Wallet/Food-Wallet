package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.operation.Operation;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OperationOpenResponse {

    private final Long operationId;
    private final String address;
    private final String time;
    private final double latitude;
    private final double longitude;

    @Builder
    private OperationOpenResponse(Long operationId, String address, String time, double latitude, double longitude) {
        this.operationId = operationId;
        this.address = address;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static OperationOpenResponse of(Operation operation) {
        return OperationOpenResponse.builder()
            .operationId(operation.getId())
            .address(operation.getAddress())
            .time(operation.getTime())
            .latitude(operation.getCoordinate().getLatitude())
            .longitude(operation.getCoordinate().getLongitude())
            .build();
    }
}
