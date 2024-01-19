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
    private Double longitude;

    @Builder
    private OperationalInfo(String address, String openTime, Double latitude, Double longitude) {
        this.address = address;
        this.openTime = openTime;
        this.latitude = validLatitude(latitude);
        this.longitude = validLongitude(longitude);
    }

    private Double validLatitude(Double target) {
        if ((target == null) || (33 <= target && target <= 43)) {
            return target;
        }
        throw new IllegalArgumentException("위도는 최소 33에서 최대 43이다.");
    }

    private Double validLongitude(Double target) {
        if ((target == null) || (124 <= target && target <= 132)) {
            return target;
        }
        throw new IllegalArgumentException("경도는 최소 124에서 최대 132이다.");
    }
}
