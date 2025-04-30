package com.ecommerce.backend.utils;

import com.ecommerce.backend.annotaions.AllowedFilter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterUtils {
//    public static List<String> getAllowedFilterFields(Class<?> dtoClass) {
//        return Arrays.stream(dtoClass.getDeclaredFields())
//                .filter(field -> field.isAnnotationPresent(AllowedFilter.class))
//                .map(Field::getName)
//                .collect(Collectors.toList());
//    }

    public static Map<String,String> getAllowedFilterFieldMappings(Class<?> dtoClass) {
        return Arrays.stream(dtoClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(AllowedFilter.class))
                .collect(Collectors.toMap(
                        Field::getName,
                        f -> {
                            String p = f.getAnnotation(AllowedFilter.class).path();
                            return p.isEmpty() ? f.getName() : p;
                        }
                ));
    }
}

