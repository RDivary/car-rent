package com.divary.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BasePage {
    private String sortBy;
    private String orderBy;
    private int page;
    private int size;
}
