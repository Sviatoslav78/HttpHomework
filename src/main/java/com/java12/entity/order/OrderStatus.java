package com.java12.entity.order;

public enum  OrderStatus {

    placed("Order is placed"),
    approved("Order is approved"),
    delivered("Order is delivered");

    private String description;

    OrderStatus(String description) {
        this.description = description;
    }
}

