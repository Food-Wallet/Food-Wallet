package com.foodwallet.server.api.controller.store.request;

import com.foodwallet.server.api.service.store.request.StoreOpenServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static com.foodwallet.server.common.message.ErrorMessage.*;

@Getter
@NoArgsConstructor
public class StoreOpenRequest {

    @NotBlank(message = NOT_BLANK_OPERATION_ADDRESS)
    private String address;

    @NotNull(message = NOT_NULL_OPERATION_START_TIME)
    private LocalTime startTime;

    @NotNull(message = NOT_NULL_OPERATION_FINISH_TIME)
    private LocalTime finishTime;

    @NotNull(message = NOT_NULL_OPERATION_LATITUDE)
    private Double latitude;

    @NotNull(message = NOT_NULL_OPERATION_LONGITUDE)
    private Double longitude;

    @Builder
    private StoreOpenRequest(String address, LocalTime startTime, LocalTime finishTime, Double latitude, Double longitude) {
        this.address = address;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StoreOpenServiceRequest toServiceRequest() {
        return StoreOpenServiceRequest.builder()
            .address(address)
            .startTime(startTime)
            .finishTime(finishTime)
            .latitude(latitude)
            .longitude(longitude)
            .build();
    }
}
