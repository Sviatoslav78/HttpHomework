package com.java12.entity.pet;


public enum Status {
    available("pet is available"),
    pending("pet is pending"),
    sold("pet is sold");
    private String description;


    Status(String description) {
        this.description = description;
    }
}
