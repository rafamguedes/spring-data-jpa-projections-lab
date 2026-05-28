package com.api.list.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemObjectDTO {
    private String itemName;
    private String itemDescription;
    private String supplierName;
}
