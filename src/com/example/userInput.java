package com.example;

import java.util.Scanner;

public class userInput {
    public static String playerInput() {
        System.out.println("Type your command");
        Scanner input = new Scanner(System.in);
         String playerinput = input.nextLine();
        return playerinput;
    }
}
