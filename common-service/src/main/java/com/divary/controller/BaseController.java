package com.divary.controller;

import com.divary.dto.BaseResponse;
import com.divary.dto.request.BasePage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class BaseController {
    protected <T>ResponseEntity<BaseResponse<T>> getResponseOk(T data, String message){
        return ResponseEntity.status(HttpStatus.OK).body(getResponse(data, message, HttpStatus.OK));
    }

    protected <T>ResponseEntity<BaseResponse<T>> getResponseCreated(T data, String message){
        return ResponseEntity.status(HttpStatus.CREATED).body(getResponse(data, message, HttpStatus.CREATED));
    }

    protected <T>BaseResponse<T> getResponse(T data, String message, HttpStatus httpStatus){
        return BaseResponse.<T>builder()
                .data(data)
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .build();
    }

    protected Pageable getPageable(String sortBy, String orderBy, int page, int size){
        BasePage basePage = BasePage.builder()
                .sortBy(sortBy)
                .orderBy(orderBy)
                .page(page)
                .size(size)
                .build();
        return getPageable(basePage);
    }

    protected Pageable getPageable(BasePage filter){
        return PageRequest.of(
                filter.getPage() == 0 ? 0 : filter.getPage() - 1,
                filter.getSize() == 0 ? 100 : filter.getSize(),
                getOrderBy(filter.getOrderBy()),
                Objects.isNull(filter.getSortBy()) ? "id" : filter.getSortBy()
        );
    }

    private Sort.Direction getOrderBy(String orderBy){
        Sort.Direction result = Sort.Direction.ASC;
        if (StringUtils.hasText(orderBy) && (Sort.Direction.ASC.toString().equalsIgnoreCase(orderBy) || Sort.Direction.DESC.toString().equalsIgnoreCase(orderBy))){
            result = Sort.Direction.fromString(orderBy);
        }
        return result;
    }
}
