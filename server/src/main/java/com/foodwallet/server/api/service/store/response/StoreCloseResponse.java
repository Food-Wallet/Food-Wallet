package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.store.OperationalInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreCloseResponse {

    private final String name;
    private final String address;
    private final String openTime;
    private final Double latitude;
    private final Double longitude;
    private final LocalDateTime closedDateTime;

    @Builder
    private StoreCloseResponse(String name, String address, String openTime, Double latitude, Double longitude, LocalDateTime closedDateTime) {
        this.name = name;
        this.address = address;
        this.openTime = openTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.closedDateTime = closedDateTime;
    }

    public static StoreCloseResponse of(String name, OperationalInfo operationalInfo) {
        return StoreCloseResponse.builder()
            .name(name)
            .address(operationalInfo.getAddress())
            .openTime(operationalInfo.getOpenTime())
            .latitude(operationalInfo.getLatitude())
            .longitude(operationalInfo.getLongitude())
            .closedDateTime(LocalDateTime.now())
            .build();
    }
}
