package com.java12.entity.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

    private long id;
    private long petId;
    private long quantity;
    private String shipDate;
    private OrderStatus status;
    private boolean complete;
}
