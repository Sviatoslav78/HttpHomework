package com.java12.commands;

public enum PetCommands {
    GET_BY_ID("1. Get pet by id"),
    ADD("2. Post pet to store"),
    PUT("3. Put pet to store"),
    GET_BY_STATUS("4. Get pet by status"),
    UPDATE("5. Update pet"),
    DELETE("6. Delete by id"),
    UPLOAD_IMAGE("7. Upload image"),
    EXIT("8. To main menu"),
    UNKNOWN("No such command");

    private String description;

    public String getDescription() {
        return description;
    }

    PetCommands(String description) {
        this.description = description;
    }
}
