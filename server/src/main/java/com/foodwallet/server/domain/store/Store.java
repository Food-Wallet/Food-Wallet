package com.foodwallet.server.domain.store;

import com.foodwallet.server.domain.BaseEntity;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.domain.store.StoreStatus.CLOSE;
import static com.foodwallet.server.domain.store.StoreStatus.OPEN;
import static org.springframework.util.StringUtils.*;

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
        this.name = validLength(name, 20);
        this.description = validLength(description, 200);
        this.image = image;
        this.reviewInfo = reviewInfo;
        this.operationalInfo = operationalInfo;
        this.member = member;
    }

    public static Store createStore(StoreType type, String name, String description, Member member) {
        return Store.builder()
            .status(StoreStatus.CLOSE)
            .type(type)
            .name(name)
            .description(description)
            .reviewInfo(
                ReviewInfo.builder()
                    .reviewCount(0)
                    .avgRate(0.0)
                    .build()
            )
            .member(member)
            .build();
    }

    public void modifyInfo(StoreType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public void open(String address, String openTime, double latitude, double longitude) {
        status = OPEN;
        operationalInfo = OperationalInfo.builder()
            .address(address)
            .openTime(openTime)
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }

    public void close() {
        status = CLOSE;
        operationalInfo = null;
    }

    public void modifyImage(UploadFile image) {
        this.image = image;
    }

    public boolean isMine(Member member) {
        return this.member.getId().equals(member.getId());
    }

    private String validLength(String target, int maxLength) {
        if (hasText(target) && target.length() > maxLength) {
            throw new IllegalArgumentException(String.format("길이는 최대 %d자 입니다.", maxLength));
        }
        return target;
    }
}
