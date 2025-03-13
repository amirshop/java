package com.ecommerce.backend.dto;

import com.ecommerce.backend.enums.FilterType;
import lombok.Data;

@Data
public class FilterCriteria {
    private String field;      // نام ستون (مثلاً "price", "name", "createdAt" و …)
    private FilterType operator;   // نوع فیلتر (مثلاً "lessThan", "contains", "inRange" و …)
    private Object criteria;    // مقدار فیلتر؛ توجه کنید که در برخی موارد آرایه (inRange) خواهد بود
}
