package com.api.list.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFilterDTO {

    private String search;

    private String itemName;

    private String itemDescription;

    private String supplierName;
}
