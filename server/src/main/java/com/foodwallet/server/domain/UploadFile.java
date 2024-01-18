package com.foodwallet.server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Column(nullable = false, updatable = false)
    private String uploadFileName;

    @Column(nullable = false, updatable = false)
    private String storeFileName;

    @Builder
    private UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
