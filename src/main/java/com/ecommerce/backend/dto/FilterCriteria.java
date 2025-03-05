package com.ecommerce.backend.dto;

import com.ecommerce.backend.enums.FilterType;
import lombok.Data;

@Data
public class FilterCriteria {
    private String col;      // نام ستون (مثلاً "price", "name", "createdAt" و …)
    private FilterType filter;   // نوع فیلتر (مثلاً "lessThan", "contains", "inRange" و …)
    private Object value;    // مقدار فیلتر؛ توجه کنید که در برخی موارد آرایه (inRange) خواهد بود
}
