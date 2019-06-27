package com.java12.commands;

public enum OrderCommands {
    ADD_ORDER("1. Add order to pet"),
    GET_INVENTORY("2. Get pet inventory"),
    FIND_ORDER("3. Find order by id"),
    DELETE_ORDER("4. Delete order by id"),
    EXIT("5. To main menu"),
    UNKNOWN("No such command");

    private String description;

    public String getDescription() {
        return description;
    }

    OrderCommands(String description) {
        this.description = description;
    }
}
