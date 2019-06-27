package com.java12;

import com.google.gson.Gson;

import java.util.Scanner;

public interface ConsoleInterface {
    Scanner scanner = new Scanner(System.in);

    Gson gson = new Gson();

    void execute();
}
