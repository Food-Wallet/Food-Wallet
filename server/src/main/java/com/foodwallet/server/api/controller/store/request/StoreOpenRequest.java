package com.foodwallet.server.api.controller.store.request;

import com.foodwallet.server.api.service.store.request.StoreOpenServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreOpenRequest {

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    @NotBlank(message = "운영 시간은 필수입니다.")
    private String openTime;

    @NotNull(message = "위도는 필수입니다.")
    private Double latitude;

    @NotNull(message = "경도는 필수입니다.")
    private Double longitude;

    @Builder
    private StoreOpenRequest(String address, String openTime, Double latitude, Double longitude) {
        this.address = address;
        this.openTime = openTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StoreOpenServiceRequest toServiceRequest() {
        return StoreOpenServiceRequest.builder()
            .address(address)
            .openTime(openTime)
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }
}
