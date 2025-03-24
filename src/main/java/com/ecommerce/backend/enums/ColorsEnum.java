package com.ecommerce.backend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum ColorsEnum {
    PRIMARY("var(--primary-color)");

    private final String value;

    ColorsEnum(String value) {
        this.value = value;
    }

}
