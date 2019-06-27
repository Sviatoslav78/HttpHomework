package com.java12.console;

import com.java12.commands.PetCommands;

public class CommandPetUtils {
    public PetCommands getPetCommand(String command) {

        if (command.trim().equals("1")) return PetCommands.GET_BY_ID;
        if (command.trim().equals("2")) return PetCommands.ADD;
        if (command.trim().equals("3")) return PetCommands.PUT;
        if (command.trim().equals("4")) return PetCommands.GET_BY_STATUS;
        if (command.trim().equals("5")) return PetCommands.UPDATE;
        if (command.trim().equals("6"))return PetCommands.DELETE;
        if (command.trim().equals("7")) return PetCommands.UPLOAD_IMAGE;
        if (command.trim().equals("8")) return PetCommands.EXIT;

        else return PetCommands.UNKNOWN;
    }
}
