package com.foodwallet.server.domain.operation;

import com.foodwallet.server.domain.BaseEntity;
import com.foodwallet.server.domain.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Operation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operation_id")
    private Long id;

    @Column(nullable = false, updatable = false, length = 20)
    private String address;

    @Column(nullable = false, updatable = false, length = 20)
    private String time;

    @Embedded
    private Coordinate coordinate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime startedDateTime;

    @Column(nullable = false)
    private LocalDateTime finishedDateTime;

    @Column(nullable = false, insertable = false, columnDefinition = "int default 0")
    private int totalSales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    private Operation(String address, String time, Coordinate coordinate, LocalDateTime startedDateTime, LocalDateTime finishedDateTime, int totalSales, Store store) {
        this.address = address;
        this.time = time;
        this.coordinate = coordinate;
        this.startedDateTime = startedDateTime;
        this.finishedDateTime = finishedDateTime;
        this.totalSales = totalSales;
        this.store = store;
    }
}
