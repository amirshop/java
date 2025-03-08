package com.ecommerce.backend.specification;

import com.ecommerce.backend.dto.FilterCriteria;
import com.ecommerce.backend.entity.product.Brand;
import com.ecommerce.backend.enums.FilterType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class BrandSpecification {

    public static Specification<Brand> createSpecificationForFilter(FilterCriteria filter) {
        return (root, query, criteriaBuilder) -> {
            String field = filter.getCol();
            FilterType operator = filter.getFilter();
            Object value = filter.getValue();

            switch (operator) {
                case LESS_THAN:
                    return criteriaBuilder.lt(root.get(field), Double.valueOf(value.toString()));
                case GREATER_THAN:
                    return criteriaBuilder.gt(root.get(field), Double.valueOf(value.toString()));
                case IN_RANGE:
                    if (value instanceof List) {
                        List<?> values = (List<?>) value;
                        if (values.size() >= 2) {
                            // فرض بر این است که فیلد از نوع Comparable است (مانند عدد یا تاریخ)
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
        };
    }
}
