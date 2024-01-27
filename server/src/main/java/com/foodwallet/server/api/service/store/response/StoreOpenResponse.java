package com.foodwallet.server.api.service.store.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoreOpenResponse {

    private final String name;
    private final String address;
    private final String openTime;
    private final Double latitude;
    private final Double longitude;
    private final LocalDateTime openDateTime;

    @Builder
    private StoreOpenResponse(String name, String address, String openTime, Double latitude, Double longitude, LocalDateTime openDateTime) {
        this.name = name;
        this.address = address;
        this.openTime = openTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openDateTime = openDateTime;
    }
}
