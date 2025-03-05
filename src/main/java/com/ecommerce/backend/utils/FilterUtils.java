package com.ecommerce.backend.utils;

import com.ecommerce.backend.annotaions.AllowedFilter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterUtils {
    public static List<String> getAllowedFilterFields(Class<?> dtoClass) {
        return Arrays.stream(dtoClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(AllowedFilter.class))
                .map(Field::getName)
                .collect(Collectors.toList());
    }
}
