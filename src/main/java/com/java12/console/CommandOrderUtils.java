package com.java12.console;


import com.java12.commands.OrderCommands;

public class CommandOrderUtils {
    public OrderCommands getOrderCommand(String command) {

        if (command.trim().equals("1")) return OrderCommands.ADD_ORDER;
        if (command.trim().equals("2")) return OrderCommands.GET_INVENTORY;
        if (command.trim().equals("3")) return OrderCommands.FIND_ORDER;
        if (command.trim().equals("4")) return OrderCommands.DELETE_ORDER;
        if (command.trim().equals("5")) return OrderCommands.EXIT;

        else return OrderCommands.UNKNOWN;
    }
}
