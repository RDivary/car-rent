package com.divary.carservice.dto.request.brand;

import com.divary.carservice.constant.ValidationConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrUpdateBrandRequest {
    @NotBlank(message = ValidationConstant.PARAM_IN_EXIST)
    private String name;
}
