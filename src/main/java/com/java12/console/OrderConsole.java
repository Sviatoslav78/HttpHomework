package com.java12.console;

import com.java12.App;
import com.java12.ConsoleInterface;
import com.java12.ConstUrls;
import com.java12.commands.OrderCommands;
import com.java12.entity.order.Order;
import com.java12.entity.order.OrderStatus;
import com.java12.service.OrderService;

import java.io.IOException;

public class OrderConsole implements ConsoleInterface {

    private CommandOrderUtils commandOrderUtils = new CommandOrderUtils();
    private OrderService orderService = new OrderService();

    @Override
    public void execute() {
        System.out.println("\tHello, here you can do actions with orders");

        OrderCommands orderCommand;

        while (true) {
            System.out.println("\n");
            for (OrderCommands commands : OrderCommands.values()) {
                if (!commands.equals(OrderCommands.UNKNOWN))
                    System.out.println(commands.getDescription());
            }
            System.out.print("\nEnter command: ");

            orderCommand = commandOrderUtils.getOrderCommand(scanner.nextLine());

            switch (orderCommand) {
                case ADD_ORDER:
                    addOrderToPet();
                    break;

                case GET_INVENTORY:
                    getPetInventory();
                    break;

                case FIND_ORDER:
                    findPurchaseById();
                    break;

                case DELETE_ORDER:
                    deleteOrderById();
                    break;

                case EXIT:
                    App.menuWork();

                case UNKNOWN:
                    System.out.println("No such command, try again\n");
                    break;
            }
        }
    }

    public void addOrderToPet() {
        Order order = Order.builder().build();
        System.out.println("Enter info for new order: ");
        System.out.println("Enter id: ");
        order.setId(Integer.parseInt(scanner.nextLine()));
        System.out.println("Enter quality: ");
        order.setQuantity(Long.parseLong(scanner.nextLine()));
        System.out.println("Enter petId");
        order.setPetId(Long.parseLong(scanner.nextLine()));
        System.out.println("Enter shipDate: ");
        order.setShipDate(scanner.nextLine());
        order.setComplete(false);
        OrderStatus status = null;
        while (status == null) {
            System.out.println("Enter status: ");
            status = getStatus(scanner.nextLine());
            if (status == null) {
                System.out.println("Sorry, unknown status");
            } else {
                order.setStatus(status);
            }
        }

        try {
            System.out.println(orderService.addOrderToPet(order));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getPetInventory() {
        try {
            String answer = orderService.getPetInventory();
            if (answer.startsWith(ConstUrls.errorType)) {
                System.out.println(answer);
            } else {
                System.out.println("Your inventory: ");
                answer = answer.substring(1, answer.length() - 1);
                String[] answerArr = answer.split(",");
                for (int i = 0; i < answerArr.length; i++) {
                    System.out.println(answerArr[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findPurchaseById() {
        System.out.println("Enter id: ");
        try {
            String answer = orderService.findPurchaseById(Integer.parseInt(scanner.nextLine()));
            if (answer.startsWith(ConstUrls.errorType)) {
                System.out.println(answer);
            } else {
                Order order = gson.fromJson(answer, Order.class);
                System.out.println(order.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderById() {
        System.out.println("Enter id: ");
        try {
            String answer = orderService.deleteOrderById(Integer.parseInt(scanner.nextLine()));
            if (answer.startsWith(ConstUrls.errorType)) {
                System.out.println(answer);
            } else {
                Order order = gson.fromJson(answer, Order.class);
                System.out.println(order.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // service extra method
    private OrderStatus getStatus(String status) {
        if (status.equalsIgnoreCase("placed")) {
            return OrderStatus.placed;
        }
        if (status.equalsIgnoreCase("approved")) {
            return OrderStatus.approved;
        }
        if (status.equalsIgnoreCase("delivered")) {
            return OrderStatus.delivered;
        }
        return null;

    }
}
