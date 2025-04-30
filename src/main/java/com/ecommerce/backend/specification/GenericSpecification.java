package com.ecommerce.backend.specification;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.enums.FilterType;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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

        switch (operator) {
            case LESS_THAN:
                return criteriaBuilder.lt(buildPath(root, field).as(Number.class), Double.valueOf(value.toString()));
            case GREATER_THAN:
                return criteriaBuilder.gt(buildPath(root, field).as(Number.class), Double.valueOf(value.toString()));
            case IN_RANGE:
                if (value instanceof List) {
                    List<?> values = (List<?>) value;
                    if (values.size() >= 2) {
                        // Assumes the field type is Comparable (like a number or a date)
                        return criteriaBuilder.between(comparablePath(root, field),
                                (Comparable) values.get(0),
                                (Comparable) values.get(1));
                    }
                }
                break;
            case CONTAINS:
                return criteriaBuilder.like(buildPath(root, field).as(String.class), "%" + value.toString() + "%");
            case NOT_CONTAINS:
                return criteriaBuilder.notLike(buildPath(root, field).as(String.class), "%" + value.toString() + "%");
            case STARTS_WITH:
                return criteriaBuilder.like(buildPath(root, field).as(String.class), value.toString() + "%");
            case ENDS_WITH:
                return criteriaBuilder.like(buildPath(root, field).as(String.class), "%" + value.toString());
            case EQUALS:
                return criteriaBuilder.equal(buildPath(root, field), value);
            case NOT_EQUAL:
                return criteriaBuilder.notEqual(buildPath(root, field), value);
            case BEFORE:
                return criteriaBuilder.lessThan(buildPath(root, field).as(String.class), (Comparable) value);
            case AFTER:
                return criteriaBuilder.greaterThan(buildPath(root, field).as(String.class), (Comparable) value);
            case BLANK:
                return criteriaBuilder.or(
                        criteriaBuilder.isNull(buildPath(root, field)),
                        criteriaBuilder.equal(buildPath(root, field), ""));
            case NOT_BLANK:
                return criteriaBuilder.and(
                        criteriaBuilder.isNotNull(buildPath(root, field)),
                        criteriaBuilder.notEqual(buildPath(root, field), ""));
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

    @SuppressWarnings("unchecked")
    private <Y extends Comparable<? super Y>> Expression<Y> comparablePath(Root<T> root, String field) {
        return (Expression<Y>) buildPath(root, field).as(Comparable.class);
    }
}

