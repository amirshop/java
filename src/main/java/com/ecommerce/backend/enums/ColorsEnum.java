package com.ecommerce.backend.enums;

import lombok.Getter;

@Getter
public enum ColorsEnum {
    PRIMARY("var(--primary-color)");

    private final String value;

    ColorsEnum(String value) {
        this.value = value;
    }

}
