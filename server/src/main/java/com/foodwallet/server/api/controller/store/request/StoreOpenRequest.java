package com.foodwallet.server.api.controller.store.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreOpenRequest {

    private String address;
    private String openTime;
    private Double latitude;
    private Double longitude;

    @Builder
    private StoreOpenRequest(String address, String openTime, Double latitude, Double longitude) {
        this.address = address;
        this.openTime = openTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
