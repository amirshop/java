package com.ecommerce.backend.enums;

import lombok.Getter;

@Getter
public enum LanguagesEnum {
    FARSI("fa"),
    ENGLISH("en"),
    AZERBAIJANI("az");

    private final String code;

    LanguagesEnum(String code) {
        this.code = code;
    }

}

