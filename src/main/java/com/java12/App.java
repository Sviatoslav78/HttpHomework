package com.java12;

import com.java12.console.OrderConsole;
import com.java12.console.PetConsole;

import java.io.IOException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws IOException {

        menuWork();
    return;
    }

    public static void menuWork() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("1. Pet console" +
                "\n2. Order console\n3. Exit\nChoose your number: ");


        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("1")) {
                new PetConsole().execute();
                break;
            } else if (line.equals("2")) {
                new OrderConsole().execute();
                break;
            } else if (line.contains("3")) System.exit(0);

            else {
                System.out.println("Unknown command");
            }
        }
    }
}
