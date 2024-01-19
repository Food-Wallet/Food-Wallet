package com.foodwallet.server.domain.menu;

import com.foodwallet.server.domain.BaseEntity;
import com.foodwallet.server.domain.UploadFile;
import com.foodwallet.server.domain.store.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static org.springframework.util.StringUtils.hasText;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(length = 200)
    private String description;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(20) default 'SELLING'", length = 20)
    private SellingStatus status;

    @Embedded
    private UploadFile image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    private Menu(String name, String description, int price, SellingStatus status, UploadFile image, Store store) {
        this.name = validLength(name, 20);
        this.description = validLength(description, 200);
        this.price = price;
        this.status = status;
        this.image = image;
        this.store = store;
    }

    public static Menu createMenu(String name, String description, int price, UploadFile image, Store store) {
        return Menu.builder()
            .name(name)
            .description(description)
            .price(price)
            .status(SellingStatus.SELLING)
            .image(image)
            .store(store)
            .build();
    }

    public void modifyInfo(String name, String description, int price) {
        this.name = validLength(name, 20);
        this.description = validLength(description, 200);
        this.price = price;
    }

    public void modifyImage(UploadFile image) {
        this.image = image;
    }

    private String validLength(String target, int maxLength) {
        if (hasText(target) && target.length() > maxLength) {
            throw new IllegalArgumentException(String.format("길이는 최대 %d자 입니다.", maxLength));
        }
        return target;
    }
}
