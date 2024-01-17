package com.foodwallet.server.api.service.store.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreResponse {

    private final Long storeId;
    private final String type;
    private final String name;
    private final String address;
    private final String openTime;
    private final String storeImage;
    private final double avgRate;

    @Builder
    private StoreResponse(Long storeId, String type, String name, String address, String openTime, String storeImage, double avgRate) {
        this.storeId = storeId;
        this.type = type;
        this.name = name;
        this.address = address;
        this.openTime = openTime;
        this.storeImage = storeImage;
        this.avgRate = avgRate;
    }
}
