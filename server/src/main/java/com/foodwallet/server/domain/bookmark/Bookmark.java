package com.foodwallet.server.domain.bookmark;

import com.foodwallet.server.domain.BaseEntity;
import com.foodwallet.server.domain.member.Member;
import com.foodwallet.server.domain.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(BookmarkId.class)
public class Bookmark extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    private Bookmark(Member member, Store store) {
        this.member = member;
        this.store = store;
    }
}
