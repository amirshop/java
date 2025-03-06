package com.ecommerce.backend.dto.account;

import com.ecommerce.backend.enums.ColorsEnum;
import com.ecommerce.backend.enums.ComponentsSizesEnum;
import com.ecommerce.backend.enums.DirectionsEnum;
import com.ecommerce.backend.enums.LanguagesEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DashboardSettingDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String name;
    private String slug;
    private String description;
    private String logo;
    private String favicon;
    private ColorsEnum primaryColor;
    private DirectionsEnum direction;
    private LanguagesEnum language;
    private ComponentsSizesEnum size;
}
