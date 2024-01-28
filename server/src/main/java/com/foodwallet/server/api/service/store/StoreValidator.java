package com.foodwallet.server.api.service.store;

import static com.foodwallet.server.common.message.ErrorMessage.*;
import static org.springframework.util.StringUtils.hasText;

public abstract class StoreValidator {

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private static final int MAX_ADDRESS_LENGTH = 30;
    private static final double MIN_LATITUDE_SIZE = 33;
    private static final double MAX_LATITUDE_SIZE = 43;
    private static final double MIN_LONGITUDE_SIZE = 124;
    private static final double MAX_LONGITUDE_SIZE = 132;

    public static String nameValidation(String name) {
        if (MAX_NAME_LENGTH < name.strip().length()) {
            throw new IllegalArgumentException(SIZE_STORE_NAME);
        }

        return name.strip();
    }

    public static String descriptionValidation(String description) {
        if (hasText(description) && MAX_DESCRIPTION_LENGTH < description.length()) {
            throw new IllegalArgumentException(SIZE_STORE_DESCRIPTION);
        }

        return description;
    }

    public static String addressValidation(String address) {
        if (MAX_ADDRESS_LENGTH < address.strip().length()) {
            throw new IllegalArgumentException(SIZE_OPERATION_ADDRESS);
        }

        return address.strip();
    }

    public static double latitudeValidation(double latitude) {
        if (latitude < MIN_LATITUDE_SIZE || MAX_LATITUDE_SIZE < latitude) {
            throw new IllegalArgumentException(SIZE_LATITUDE_ADDRESS);
        }
        return latitude;
    }

    public static double longitudeValidation(double longitude) {
        if (longitude < MIN_LONGITUDE_SIZE || MAX_LONGITUDE_SIZE < longitude) {
            throw new IllegalArgumentException(SIZE_LONGITUDE_ADDRESS);
        }
        return longitude;
    }
}
