package com.divary.carservice.service;

import com.divary.carservice.dto.BrandDto;
import com.divary.carservice.dto.request.brand.CreateOrUpdateBrandRequest;
import com.divary.carservice.model.Brand;

import java.util.List;

public interface BrandService {
    void create(CreateOrUpdateBrandRequest form);
    List<BrandDto> findAll();
    Brand findById(String id);
    void update(String id, CreateOrUpdateBrandRequest form);
    void delete(String id);
}
