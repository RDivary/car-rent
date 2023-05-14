package com.divary.carservice.dto;

import com.divary.carservice.model.Brand;
import lombok.Getter;

@Getter
public class BrandDto {
    private final String id;
    private final String name;
    private final boolean isDelete;

    public BrandDto(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.isDelete = brand.isDelete();
    }
}
