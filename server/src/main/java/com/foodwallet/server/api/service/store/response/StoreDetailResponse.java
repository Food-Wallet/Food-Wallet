package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.api.service.menu.response.MenuResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class StoreDetailResponse {

    private final Long storeId;
    private final String type;
    private final String name;
    private final String description;
    private final String storeImage;
    private final double avgRate;
    private final String status;
    private final String address;
    private final String openTime;
    private final List<MenuResponse> menus;

    @Builder
    private StoreDetailResponse(Long storeId, String type, String name, String description, String storeImage, double avgRate, String status, String address, String openTime, List<MenuResponse> menus) {
        this.storeId = storeId;
        this.type = type;
        this.name = name;
        this.description = description;
        this.storeImage = storeImage;
        this.avgRate = avgRate;
        this.status = status;
        this.address = address;
        this.openTime = openTime;
        this.menus = menus;
    }
}
