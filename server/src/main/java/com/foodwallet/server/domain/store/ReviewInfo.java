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
public class ReviewInfo {

    @Column(nullable = false, insertable = false, columnDefinition = "int default 0")
    private int reviewCount;

    @Column(nullable = false, insertable = false, columnDefinition = "decimal(3, 2) default 0.0")
    private double avgRate;

    @Builder
    private ReviewInfo(int reviewCount, double avgRate) {
        this.reviewCount = reviewCount;
        this.avgRate = avgRate;
    }
}
