package com.microserivce.eurekaserver.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;

}
