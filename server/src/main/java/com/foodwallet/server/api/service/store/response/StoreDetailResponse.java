package com.foodwallet.server.api.service.store.response;

import com.foodwallet.server.domain.menu.repository.response.MenuResponse;
import com.foodwallet.server.domain.store.repository.dto.StoreDetailDto;
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

    public static StoreDetailResponse of(StoreDetailDto store, List<MenuResponse> menus) {
        return StoreDetailResponse.builder()
            .storeId(store.getStoreId())
            .type(store.getType())
            .name(store.getName())
            .description(store.getDescription())
            .storeImage(store.getStoreImage())
            .avgRate(store.getAvgRate())
            .status(store.getStatus())
            .address(store.getAddress())
            .openTime(store.getOpenTime())
            .menus(menus)
            .build();
    }
}
