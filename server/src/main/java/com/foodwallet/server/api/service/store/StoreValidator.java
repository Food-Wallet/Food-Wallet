package com.foodwallet.server.api.service.store;

import static com.foodwallet.server.common.message.ErrorMessage.SIZE_STORE_DESCRIPTION;
import static com.foodwallet.server.common.message.ErrorMessage.SIZE_STORE_NAME;
import static org.springframework.util.StringUtils.hasText;

public abstract class StoreValidator {

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

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
}
