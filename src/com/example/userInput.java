package com.example;

import java.util.Scanner;

public class userInput {
    public static String playerInput() {

        System.out.println("Type your command");
        Scanner input = new Scanner(System.in);
        String playerinput = input.nextLine();
        playerinput = playerinput.toLowerCase().trim().replaceAll(" +", " ");
        if (playerinput.equalsIgnoreCase("Exit") || playerinput.equalsIgnoreCase("QUIT")) {
            System.out.print("Thanks for playing");
            System.exit(0);
        }
        return playerinput;
    }

    public static String website() {
        System.out.println("Enter a URL");
        Scanner input = new Scanner(System.in);
        String url = input.nextLine();

        return url;
    }
}

