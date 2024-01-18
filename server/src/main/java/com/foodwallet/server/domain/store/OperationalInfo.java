package com.foodwallet.server.domain.store;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OperationalInfo {

    @Column(insertable = false, length = 20)
    private String address;

    @Column(insertable = false, length = 20)
    private String openTime;

    @Column(insertable = false)
    private Double latitude;

    @Column(insertable = false)
    private double longitude;

    @Builder
    private OperationalInfo(String address, String openTime, Double latitude, double longitude) {
        this.address = address;
        this.openTime = openTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
