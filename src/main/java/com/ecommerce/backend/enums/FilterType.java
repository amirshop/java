package com.ecommerce.backend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum FilterType {
    LESS_THAN,
    GREATER_THAN,
    IN_RANGE,
    CONTAINS,
    NOT_CONTAINS,
    STARTS_WITH,
    ENDS_WITH,
    EQUALS,
    NOT_EQUAL,
    BEFORE,
    AFTER,
    BLANK,
    NOT_BLANK;
}
