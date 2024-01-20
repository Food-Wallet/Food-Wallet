package com.foodwallet.server.domain.store.repository.response;

import com.foodwallet.server.domain.store.StoreType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    public StoreResponse(Long storeId, StoreType type, String name, String address, String openTime, String storeImage, double avgRate) {
        this.storeId = storeId;
        this.type = type.getText();
        this.name = name;
        this.address = address;
        this.openTime = openTime;
        this.storeImage = storeImage;
        this.avgRate = avgRate;
    }
}
