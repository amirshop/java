package com.ecommerce.backend.specification;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.enums.FilterType;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

public class GenericSpecification<T> implements Specification<T> {
    private final FilterCriteria filter;

    public GenericSpecification(FilterCriteria filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String field = filter.getField();
        FilterType operator = filter.getOperator();
        Object value = filter.getCriteria();
        Path<?> path = buildPath(root, field);
        Object coercedValue = coerceValueToFieldType(path, value);

        switch (operator) {
            case LESS_THAN:
                return criteriaBuilder.lt(path.as(Number.class), Double.valueOf(value.toString()));
            case GREATER_THAN:
                return criteriaBuilder.gt(path.as(Number.class), Double.valueOf(value.toString()));
            case IN_RANGE:
                if (value instanceof List) {
                    List<?> values = (List<?>) value;
                    if (values.size() >= 2) {
                        // Assumes the field type is Comparable (like a number or a date)
                        return criteriaBuilder.between(root.get(field),
                                (Comparable) values.get(0),
                                (Comparable) values.get(1));
                    }
                }
                break;
            case CONTAINS:
                return criteriaBuilder.like(path.as(String.class), "%" + value.toString() + "%");
            case NOT_CONTAINS:
                return criteriaBuilder.notLike(path.as(String.class), "%" + value.toString() + "%");
            case STARTS_WITH:
                return criteriaBuilder.like(path.as(String.class), value.toString() + "%");
            case ENDS_WITH:
                return criteriaBuilder.like(path.as(String.class), "%" + value.toString());
            case EQUALS:
                return criteriaBuilder.equal(path, coercedValue);
            case NOT_EQUAL:
                return criteriaBuilder.notEqual(path, value);
            case BEFORE:
                return criteriaBuilder.lessThan(path.as(String.class), (String) value);
            case AFTER:
                return criteriaBuilder.greaterThan(path.as(String.class), (String) value);
            case BLANK:
                return criteriaBuilder.or(
                        criteriaBuilder.isNull(path),
                        criteriaBuilder.equal(path, ""));
            case NOT_BLANK:
                return criteriaBuilder.and(
                        criteriaBuilder.isNotNull(path),
                        criteriaBuilder.notEqual(path, ""));
            default:
                return null;
        }
        return null;
    }


    private Path<?> buildPath(Root<T> root, String fieldPath) {
        Path<?> path = root;
        for (String part : fieldPath.split("\\.")) {
            path = path.get(part);
        }
        return path;
    }

//    @SuppressWarnings("unchecked")
//    private <Y extends Comparable<? super Y>> Expression<Y> comparablePath(Root<T> root, String field) {
//        return (Expression<Y>) buildPath(root, field).as(Comparable.class);
//    }


    @SuppressWarnings("unchecked")
    private <Y extends Comparable<? super Y>>
    Predicate compare(CriteriaBuilder cb, Expression<?> expr, Object value, BiFunction<Expression<Y>,Y,Predicate> op) {
        Class<Y> type = (Class<Y>) value.getClass();
        Expression<Y> typed = expr.as(type);
        return op.apply(typed, (Y) value);
    }

    private Object coerceValueToFieldType(Path<?> path, Object value) {
        Class<?> type = path.getJavaType();

        if (value == null) return null;

        if (type.equals(UUID.class) && value instanceof String) {
            return UUID.fromString((String) value);
        }

        if (type.equals(Integer.class) && value instanceof String) {
            return Integer.valueOf((String) value);
        }

        if (type.equals(Long.class) && value instanceof String) {
            return Long.valueOf((String) value);
        }

        //TODO: add support for LocalDate, Enum, Boolean, etc.

        return value;
    }


}

