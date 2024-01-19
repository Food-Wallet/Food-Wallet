package com.foodwallet.server.api.service.store.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreOpenServiceRequest {

    private final String address;
    private final String openTime;
    private final double latitude;
    private final double longitude;

    @Builder
    private StoreOpenServiceRequest(String address, String openTime, Double latitude, Double longitude) {
        this.address = address;
        this.openTime = openTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
