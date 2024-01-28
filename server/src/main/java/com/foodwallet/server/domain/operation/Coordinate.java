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
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Builder
    private Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Coordinate create(double latitude, double longitude) {
        return new Coordinate(latitude, longitude);
    }
}
