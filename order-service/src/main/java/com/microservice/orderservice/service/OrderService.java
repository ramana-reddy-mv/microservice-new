package com.microserivce.eurekaserver.orderservice.service;

import com.microserivce.eurekaserver.orderservice.dto.InventoryResponse;
import com.microserivce.eurekaserver.orderservice.dto.OrderLineItemsDto;
import com.microserivce.eurekaserver.orderservice.dto.OrderRequest;
import com.microserivce.eurekaserver.orderservice.model.Order;
import com.microserivce.eurekaserver.orderservice.model.OrderLineItems;
import com.microserivce.eurekaserver.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    public WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mpaToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes =   order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //call inventory service and place order if product is in stock
        InventoryResponse[] inventoryResponsesArray  = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                .allMatch(InventoryResponse::isInStock);
        if(allProductsInStock){
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("product is not in stock please try again later");
        }

    }

    private OrderLineItems mpaToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        return orderLineItems;
    }
}


