package com.foodwallet.server.domain.operation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Coordinate {

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Builder
    private Coordinate(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
