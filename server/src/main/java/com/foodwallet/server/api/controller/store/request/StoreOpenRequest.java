package com.foodwallet.server.api.controller.store.request;

import com.foodwallet.server.api.service.store.request.StoreOpenServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.foodwallet.server.common.message.ErrorMessage.*;

@Getter
@NoArgsConstructor
public class StoreOpenRequest {

    @NotBlank(message = NOT_BLANK_STORE_ADDRESS)
    private String address;

    @NotBlank(message = NOT_BLANK_STORE_OPEN_TIME)
    private String openTime;

    @NotNull(message = NOT_NULL_STORE_LATITUDE)
    private Double latitude;

    @NotNull(message = NOT_NULL_STORE_LONGITUDE)
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
