package com.foodwallet.server.api.service.store.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreOpenServiceRequest {

    private final String address;
    private final LocalTime startTime;
    private final LocalTime finishTime;
    private final double latitude;
    private final double longitude;

    @Builder
    private StoreOpenServiceRequest(String address, LocalTime startTime, LocalTime finishTime, double latitude, double longitude) {
        this.address = address;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
