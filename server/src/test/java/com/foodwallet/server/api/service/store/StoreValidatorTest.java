package com.foodwallet.server.api.service.store;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StoreValidatorTest {

    @DisplayName("매장명의 길이가 20자를 초과하면 예외가 발생한다.")
    @Test
    void nameValidationWithOutOfLengthName() {
        //given
        String storeName = generateStr(21);

        //when //then
        assertThatThrownBy(() -> StoreValidator.nameValidation(storeName))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("매장명의 길이는 최대 20자입니다.");
    }

    @DisplayName("매장명의 길이는 최대 20자이다.")
    @Test
    void nameValidation() {
        //given
        String storeName = generateStr(20);

        //when
        String validatedName = StoreValidator.nameValidation(storeName);

        //then
        assertThat(validatedName.length()).isEqualTo(20);
    }

    @DisplayName("매장 설명의 길이가 200자를 초과하면 예외가 발생한다.")
    @Test
    void descriptionValidationWithOutOfLengthName() {
        //given
        String storeDescription = generateStr(201);

        //when //then
        assertThatThrownBy(() -> StoreValidator.descriptionValidation(storeDescription))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("매장 설명의 길이는 최대 20자입니다.");
    }

    @DisplayName("매장 설명은 null을 허용한다.")
    @Test
    void descriptionValidationWithNull() {
        //given
        String storeDescription = null;

        //when
        String validatedDescription = StoreValidator.descriptionValidation(StoreValidator.descriptionValidation(storeDescription));

        //then
        assertThat(validatedDescription).isNull();
    }

    @DisplayName("매장 설명의 길이는 최대 200자이다.")
    @Test
    void descriptionValidation() {
        //given
        String storeDescription = generateStr(200);

        //when
        String validatedDescription = StoreValidator.descriptionValidation(StoreValidator.descriptionValidation(storeDescription));

        //then
        assertThat(validatedDescription.length()).isEqualTo(200);
    }

    private String generateStr(int length) {
        return "가".repeat(length);
    }
}