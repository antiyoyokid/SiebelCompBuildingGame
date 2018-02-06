package com.example;

import java.util.Scanner;

public class userInput {
    public static String playerInput() {
        System.out.println("Type Something");
        Scanner input = new Scanner(System.in);
         String playerinput = input.next();
        return playerinput;
    }
}
