package com.api.list.service.dto;

public record ItemJPQLDTO(
        String itemName,
        String supplierName,
        Integer quantity
) {}