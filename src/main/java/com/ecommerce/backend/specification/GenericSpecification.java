package com.ecommerce.backend.specification;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.enums.FilterType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
                return criteriaBuilder.lt(root.get(field), Double.valueOf(value.toString()));
            case GREATER_THAN:
                return criteriaBuilder.gt(root.get(field), Double.valueOf(value.toString()));
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
                return criteriaBuilder.like(root.get(field), "%" + value.toString() + "%");
            case NOT_CONTAINS:
                return criteriaBuilder.notLike(root.get(field), "%" + value.toString() + "%");
            case STARTS_WITH:
                return criteriaBuilder.like(root.get(field), value.toString() + "%");
            case ENDS_WITH:
                return criteriaBuilder.like(root.get(field), "%" + value.toString());
            case EQUALS:
                return criteriaBuilder.equal(root.get(field), value);
            case NOT_EQUAL:
                return criteriaBuilder.notEqual(root.get(field), value);
            case BEFORE:
                return criteriaBuilder.lessThan(root.get(field), (Comparable) value);
            case AFTER:
                return criteriaBuilder.greaterThan(root.get(field), (Comparable) value);
            case BLANK:
                return criteriaBuilder.or(
                        criteriaBuilder.isNull(root.get(field)),
                        criteriaBuilder.equal(root.get(field), ""));
            case NOT_BLANK:
                return criteriaBuilder.and(
                        criteriaBuilder.isNotNull(root.get(field)),
                        criteriaBuilder.notEqual(root.get(field), ""));
            default:
                return null;
        }
        return null;
    }
}

