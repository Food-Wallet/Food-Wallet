package com.foodwallet.server.domain.store;

import com.foodwallet.server.domain.BaseEntity;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'CLOSE'", length = 20)
    private StoreStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StoreType type;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 200)
    private String description;

    @Embedded
    private UploadFile image;

    @Embedded
    private ReviewInfo reviewInfo;

    @Embedded
    private OperationalInfo operationalInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Store(StoreStatus status, StoreType type, String name, String description, UploadFile image, ReviewInfo reviewInfo, OperationalInfo operationalInfo, Member member) {
        this.status = status;
        this.type = type;
        this.name = name;
        this.description = description;
        this.image = image;
        this.reviewInfo = reviewInfo;
        this.operationalInfo = operationalInfo;
        this.member = member;
    }
}
