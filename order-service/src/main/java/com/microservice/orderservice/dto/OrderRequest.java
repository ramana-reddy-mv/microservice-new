package com.microserivce.eurekaserver.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private List<OrderLineItemsDto> orderLineItemsDtoList;

    public List<OrderLineItemsDto> getOrderLineItemsDtoList() {
        return orderLineItemsDtoList;
    }

    public void setOrderLineItemsDtoList(List<OrderLineItemsDto> orderLineItemsDtoList) {
        this.orderLineItemsDtoList = orderLineItemsDtoList;
    }
}
