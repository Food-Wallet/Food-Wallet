package com.foodwallet.server.domain.store.repository.dto;

import com.foodwallet.server.domain.store.StoreStatus;
import com.foodwallet.server.domain.store.StoreType;
import lombok.Getter;

@Getter
public class StoreDetailDto {

    private final Long storeId;
    private final String type;
    private final String name;
    private final String description;
    private final String storeImage;
    private final double avgRate;
    private final String status;
    private final String address;
    private final String openTime;

    public StoreDetailDto(Long storeId, StoreType type, String name, String description, String storeImage, double avgRate, StoreStatus status, String address, String openTime) {
        this.storeId = storeId;
        this.type = type.getText();
        this.name = name;
        this.description = description;
        this.storeImage = storeImage;
        this.avgRate = avgRate;
        this.status = status.getText();
        this.address = address;
        this.openTime = openTime;
    }
}
