package com.divary.carservice.service;

import com.divary.carservice.dto.BrandDto;
import com.divary.carservice.dto.request.brand.CreateOrUpdateBrandRequest;
import com.divary.carservice.model.Brand;
import com.divary.carservice.repository.BrandRepository;
import com.divary.exception.BadRequestException;
import com.divary.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final CarService carService;

    public BrandServiceImpl(BrandRepository brandRepository, @Lazy CarService carService) {
        this.brandRepository = brandRepository;
        this.carService = carService;
    }


    @Override
    public void create(CreateOrUpdateBrandRequest form) {
        Brand brand = Brand.builder()
                .name(form.getName())
                .build();
        brandRepository.save(brand);
        log.info("Create Brand success, " + brand);
    }

    @Override
    public List<BrandDto> findAll() {
        return brandRepository.findAll().stream()
                .map(BrandDto::new)
                .collect(Collectors.toList());
    }

    public Brand findById(String id) {
        return brandRepository.findById(id).orElseThrow(() -> new NotFoundException("Brand Not Found"));
    }

    @Override
    public void update(String id, CreateOrUpdateBrandRequest form) {
        Brand brand = findById(id);
        brand.setName(form.getName());

        brandRepository.save(brand);
        carService.renameBrand(id, form.getName());
        log.info("Update Brand success, " + brand);
    }

    @Override
    public void delete(String id) {
        Brand brand = findById(id);
        if (brand.isDelete()) throw new BadRequestException("Cannot delete brand, Because brand is deleted");

        brand.setDelete(true);

        brandRepository.save(brand);
        log.info("Delete Brand success, " + id);
    }
}
