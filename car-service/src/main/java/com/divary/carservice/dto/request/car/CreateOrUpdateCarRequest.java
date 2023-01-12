package com.divary.carservice.dto.request.car;

import com.divary.carservice.constant.ValidationConstant;
import com.divary.carservice.enums.Engine;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrUpdateCarRequest {
    @NotBlank(message = ValidationConstant.PARAM_IN_EXIST)
    private String brand;
    @NotBlank(message = ValidationConstant.PARAM_IN_EXIST)
    private String model;
    @Positive(message = ValidationConstant.PARAM_MORE_THAN_ZERO)
    private int passenger;
//    @ValueOfEnum(enumClass = Engine.class)
    //TODO create validation
    private Engine engine;
}
