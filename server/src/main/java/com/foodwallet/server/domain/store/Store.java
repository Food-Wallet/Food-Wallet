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

    @Column(nullable = false, insertable = false, columnDefinition = "int default 0")
    private int bookmarkCount;

    @Embedded
    private ReviewInfo reviewInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Store(StoreStatus status, StoreType type, String name, String description, UploadFile image, int bookmarkCount, ReviewInfo reviewInfo, Member member) {
        this.status = status;
        this.type = type;
        this.name = name;
        this.description = description;
        this.image = image;
        this.bookmarkCount = bookmarkCount;
        this.reviewInfo = reviewInfo;
        this.member = member;
    }

    public static Store createStore(StoreType type, String name, String description, UploadFile image, Member member) {
        ReviewInfo reviewInfo = ReviewInfo.createReviewInfo();

        return Store.builder()
            .status(CLOSE)
            .type(type)
            .name(name)
            .description(description)
            .image(image)
            .bookmarkCount(0)
            .reviewInfo(reviewInfo)
            .member(member)
            .build();
    }

    public void modifyInfo(StoreType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public void modifyImage(UploadFile image) {
        this.image = image;
    }

    public void open() {
        status = OPEN;
    }

    public void close() {
        status = CLOSE;
    }

    public void increaseBookmarkCount() {
        bookmarkCount += 1;
    }

    public void decreaseBookmarkCount() {
        bookmarkCount -= 1;
    }

    public boolean isMine(Member member) {
        return this.member.getId().equals(member.getId());
    }
}
