package com.divary.carservice.controller;

import com.divary.carservice.dto.BrandDto;
import com.divary.carservice.dto.request.brand.CreateOrUpdateBrandRequest;
import com.divary.carservice.service.BrandService;
import com.divary.controller.BaseController;
import com.divary.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/car-service/api/v1/brand")
public class BrandController extends BaseController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Object>> create(@RequestBody @Valid CreateOrUpdateBrandRequest form){
        brandService.create(form);
        return getResponseCreated(null, "Brand created");
    }

    @GetMapping()
    public ResponseEntity<BaseResponse<List<BrandDto>>> findAll(){
        return getResponseOk(brandService.findAll(), "Brand found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<BrandDto>> findById(@PathVariable String id){
        return getResponseOk(new BrandDto(brandService.findById(id)), "Brand found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> update(@PathVariable String id, @RequestBody CreateOrUpdateBrandRequest form){
        brandService.update(id, form);
        return getResponseOk(null, "Brand updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Object>> delete(@PathVariable String id){
        brandService.delete(id);
        return getResponseOk(null, "Brand deleted");
    }

}
